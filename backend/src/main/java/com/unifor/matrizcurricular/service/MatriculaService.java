package com.unifor.matrizcurricular.service;

import com.unifor.matrizcurricular.domain.Aula;
import com.unifor.matrizcurricular.domain.AulaCurso;
import com.unifor.matrizcurricular.domain.Horario;
import com.unifor.matrizcurricular.domain.Matricula;
import com.unifor.matrizcurricular.exception.BusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.json.JsonNumber;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class MatriculaService {

    @Inject
    JsonWebToken jwt;

    @Inject
    EntityManager em;

    @Transactional
    public void matricular(Long aulaId) {

        UUID alunoId = UUID.fromString(jwt.getSubject());
        Object claim = jwt.getClaim("cursoId");

        Long cursoId = switch (claim) {
            case JsonNumber jsonNumber -> jsonNumber.longValue();
            case Number number -> number.longValue();
            case String ignored -> Long.valueOf(claim.toString());
            case null, default -> throw new BusinessException("Token sem claim cursoId (configure no Keycloak)");
        };

        // Lock na aula (validar concorrência)
        Aula aula = em.find(Aula.class, aulaId, LockModeType.PESSIMISTIC_WRITE);
        if (aula == null || !aula.ativa) {
            throw new BusinessException("Aula não encontrada ou inativa");
        }

        // Aluno já matriculado?
        boolean ja = Matricula.count("alunoId = ?1 and aula.id = ?2", alunoId, aulaId) > 0;
        if (ja) throw new BusinessException("Você já está matriculado nessa aula");

        // Curso autorizado?
        boolean autorizado = AulaCurso.count("aula.id = ?1 and curso.id = ?2", aulaId, cursoId) > 0;
        if (!autorizado) throw new BusinessException("Seu curso não está autorizado para essa aula");

        // Vaga disponível?
        if (aula.vagasOcupadas >= aula.vagasMaximas) {
            throw new BusinessException("Não há vagas disponíveis");
        }

        // Conflito de horário?
        // Conflito = mesmo dia e intervalos se sobrepõem:
        // (novo.inicio < existente.fim) AND (novo.fim > existente.inicio)
        boolean conflito = existeConflitoHorario(alunoId, aula.horario.id);
        if (conflito) throw new BusinessException("Conflito de horário com aula já matriculada");

        // Cria matrícula + incrementa vaga (mesma transação)
        Matricula m = new Matricula();
        m.alunoId = alunoId;
        m.aula = aula;
        m.persist();

        aula.vagasOcupadas++;
    }

    public List<Map> listar() {
        UUID alunoId = UUID.fromString(jwt.getSubject());

        return em.createQuery("""
            select new map(
              m.id as matriculaId,
              a.id as aulaId,
              d.nome as disciplina,
              p.nome as professor,
              h.diaSemana as diaSemana,
              h.inicio as inicio,
              h.fim as fim
            )
            from Matricula m
            join m.aula a
            join a.disciplina d
            join a.professor p
            join a.horario h
            where m.alunoId = :alunoId
            """, Map.class)
                .setParameter("alunoId", alunoId)
                .getResultList();
    }

    private boolean existeConflitoHorario(UUID alunoId, Long novoHorarioId) {

        Horario novo = Horario.findById(novoHorarioId);

        Long count = em.createQuery("""
                            select count(m)
                            from Matricula m
                            join m.aula a
                            join a.horario h
                            where m.alunoId = :alunoId
                              and h.diaSemana = :dia
                              and (:novoInicio < h.fim and :novoFim > h.inicio)
                        """, Long.class)
                .setParameter("alunoId", alunoId)
                .setParameter("dia", novo.diaSemana)
                .setParameter("novoInicio", novo.inicio)
                .setParameter("novoFim", novo.fim)
                .getSingleResult();

        return count != null && count > 0;
    }
}
