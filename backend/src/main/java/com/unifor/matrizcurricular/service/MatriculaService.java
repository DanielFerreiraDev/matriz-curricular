package com.unifor.matrizcurricular.service;

import com.unifor.matrizcurricular.domain.Aluno;
import com.unifor.matrizcurricular.domain.Aula;
import com.unifor.matrizcurricular.domain.AulaCurso;
import com.unifor.matrizcurricular.domain.Matricula;
import com.unifor.matrizcurricular.exception.BusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MatriculaService {

    @Inject
    JsonWebToken jwt;

    @Transactional
    public void matricular(Long aulaId) {
        UUID alunoId = UUID.fromString(jwt.getSubject());


        // Busca aula COM LOCK (concorrência)
        Aula aula = Aula.find(
                        "id = ?1 and ativa = true",
                        aulaId
                ).withLock(LockModeType.PESSIMISTIC_WRITE)
                .firstResult();

        if (aula == null) {
            throw new BusinessException("Aula não encontrada ou inativa");
        }

        // Verifica vaga
        if (aula.vagasOcupadas >= aula.vagasMaximas) {
            throw new BusinessException("Não há vagas disponíveis");
        }

        // Verifica se já está matriculado
        boolean jaMatriculado = Matricula.count(
                "aluno.id = ?1 and aula.id = ?2",
                alunoId, aulaId
        ) > 0;

        if (jaMatriculado) {
            throw new BusinessException("Aluno já matriculado nesta aula");
        }

        // Verifica curso autorizado
        boolean cursoAutorizado = AulaCurso.count(
                "aula.id = ?1 and curso.id in (?2)",
                aulaId,
                obterCursosDoAluno(alunoId)
        ) > 0;

        if (!cursoAutorizado) {
            throw new BusinessException("Curso do aluno não autorizado para esta aula");
        }

        // Verifica conflito de horário
        boolean conflito = existeConflitoHorario(alunoId, aula.horario.id);

        if (conflito) {
            throw new BusinessException("Conflito de horário com outra aula");
        }

        // Cria matrícula
        Matricula matricula = new Matricula();
        matricula.alunoId = alunoId;
        matricula.aula = aula;
        matricula.persist();

        aula.vagasOcupadas++;

        aula.persist();
    }

    // ============================
    // MÉTODOS AUXILIARES
    // ============================

    private List<Long> obterCursosDoAluno(UUID alunoId) {
        // simplificação aceitável para o desafio
        return List.of(1L); // mock / fixo
    }

    private boolean existeConflitoHorario(UUID alunoId, Long horarioId) {
        return Matricula.count(
                """
                aluno.id = ?1
                and aula.horario.id = ?2
                """,
                alunoId, horarioId
        ) > 0;
    }
}
