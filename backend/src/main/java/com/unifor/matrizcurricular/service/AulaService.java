package com.unifor.matrizcurricular.service;

import com.unifor.matrizcurricular.api.dto.AulaCreateRequestDTO;
import com.unifor.matrizcurricular.api.dto.AulaFiltroParameters;
import com.unifor.matrizcurricular.api.dto.AulaResponseDTO;
import com.unifor.matrizcurricular.api.dto.AulaUpdateRequestDTO;
import com.unifor.matrizcurricular.domain.*;
import com.unifor.matrizcurricular.exception.BusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonNumber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AulaService {

    @Inject
    EntityManager em;

    @Inject
    JsonWebToken jwt;

    public List<AulaResponseDTO> listarAulasDoCoordenador(AulaFiltroParameters aulaFiltroParameters) {
        UUID coordenadorId = UUID.fromString(jwt.getSubject());

        StringBuilder jpql = getStringBuilder(aulaFiltroParameters);

        TypedQuery<Aula> q = em.createQuery(jpql.toString(), Aula.class);
        q.setParameter("coordId", coordenadorId);

        if (aulaFiltroParameters.diaSemana != null && !aulaFiltroParameters.diaSemana.isBlank()) q.setParameter("diaSemana", aulaFiltroParameters.diaSemana);
        if (aulaFiltroParameters.inicio != null && !aulaFiltroParameters.inicio.isBlank()) q.setParameter("inicio", LocalTime.parse(aulaFiltroParameters.inicio));
        if (aulaFiltroParameters.fim != null && !aulaFiltroParameters.fim.isBlank()) q.setParameter("fim", LocalTime.parse(aulaFiltroParameters.fim));

        if (aulaFiltroParameters.periodo != null && !aulaFiltroParameters.periodo.isBlank()) {
            String p = aulaFiltroParameters.periodo.toUpperCase();
            if (p.equals("MANHA") || p.equals("TARDE")) q.setParameter("p12", LocalTime.of(12, 0));
            if (p.equals("TARDE") || p.equals("NOITE")) q.setParameter("p18", LocalTime.of(18, 0));
        }

        if (aulaFiltroParameters.vagasMaximas != null) q.setParameter("vagasMaximas", aulaFiltroParameters.vagasMaximas);
        if (aulaFiltroParameters.cursoListIds != null && !aulaFiltroParameters.cursoListIds.isEmpty()) q.setParameter("cursoListIds", aulaFiltroParameters.cursoListIds);

        List<Aula> aulas = q.getResultList();

        return aulas.stream().map(this::toResponse).toList();
    }

    public List<AulaResponseDTO> listarAulasDisponiveisAluno() {
        UUID alunoId = UUID.fromString(jwt.getSubject());
        Object claim = jwt.getClaim("cursoId");

        Long cursoId = switch (claim) {
            case JsonNumber jsonNumber -> jsonNumber.longValue();
            case Number number -> number.longValue();
            case String s -> Long.valueOf(s.trim());
            case null, default -> throw new BusinessException("Token sem claim cursoId (configure no Keycloak)");
        };

        List<Aula> aulas = em.createQuery("""
            select distinct a
            from Aula a
            join fetch a.disciplina
            join fetch a.professor
            join fetch a.horario h
            where a.ativa = true
              and a.vagasOcupadas < a.vagasMaximas
              and exists (
                  select 1 from AulaCurso ac
                  where ac.aula.id = a.id and ac.curso.id = :cursoId
              )
              and not exists (
                  select 1 from Matricula m
                  where m.alunoId = :alunoId and m.aula.id = a.id
              )
            order by h.diaSemana, h.inicio
        """, Aula.class)
                .setParameter("cursoId", cursoId)
                .setParameter("alunoId", alunoId)
                .getResultList();

        return aulas.stream().map(this::toResponse).toList();
    }

    private static StringBuilder getStringBuilder(AulaFiltroParameters f) {
        StringBuilder jpql = new StringBuilder("""
            select distinct a
            from Aula a
            join fetch a.disciplina d
            join fetch a.professor p
            join fetch a.horario h
            where a.coordenadorId = :coordId
        """);

        if (f.diaSemana != null && !f.diaSemana.isBlank()) {
            jpql.append(" and h.diaSemana = :diaSemana ");
        }

        if (f.inicio != null && !f.inicio.isBlank()) {
            jpql.append(" and h.inicio >= :inicio ");
        }

        if (f.fim != null && !f.fim.isBlank()) {
            jpql.append(" and h.fim <= :fim ");
        }

        if (f.periodo != null && !f.periodo.isBlank()) {
            jpql.append(" and ");
            switch (f.periodo.toUpperCase()) {
                case "MANHA" -> jpql.append(" h.inicio < :p12 ");
                case "TARDE" -> jpql.append(" h.inicio >= :p12 and h.inicio < :p18 ");
                case "NOITE" -> jpql.append(" h.inicio >= :p18 ");
                default -> { }
            }
        }

        if (f.vagasMaximas != null) {
            jpql.append(" and a.vagasMaximas = :vagasMaximas ");
        }

        if (f.cursoListIds != null && !f.cursoListIds.isEmpty()) {
            jpql.append("""
              and exists (
                select 1 from AulaCurso ac
                where ac.aula.id = a.id and ac.curso.id in :cursoListIds
              )
            """);
        }
        return jpql;
    }

    @Transactional
    public Aula criarAula(AulaCreateRequestDTO requestDTO) {
        UUID coordenadorIdToken = UUID.fromString(jwt.getSubject());

        // Validação entidades base
        Disciplina disciplina = Disciplina.findById(requestDTO.disciplinaId);
        if (disciplina == null) throw new BusinessException("Disciplina não encontrada");

        Professor professor = Professor.findById(requestDTO.professorId);
        if (professor == null) throw new BusinessException("Professor não encontrado");

        Horario horario = Horario.findById(requestDTO.horarioId);
        if (horario == null) throw new BusinessException("Horário não encontrado");

        if (requestDTO.vagasMaximas == null || requestDTO.vagasMaximas <= 0) {
            throw new BusinessException("vagasMaximas deve ser > 0");
        }

        if (requestDTO.cursosAutorizadosIds == null || requestDTO.cursosAutorizadosIds.isEmpty()) {
            throw new BusinessException("Informe ao menos 1 curso autorizado");
        }

        // Regra: mesma disciplina pode repetir, mas não no mesmo horário
        long existe = Aula.count("disciplina.id = ?1 and horario.id = ?2 and ativa = true",
                requestDTO.disciplinaId, requestDTO.horarioId);
        if (existe > 0) {
            throw new BusinessException("Já existe oferta dessa disciplina nesse horário");
        }

        // Cria aula
        Aula aula = new Aula();
        aula.disciplina = disciplina;
        aula.professor = professor;
        aula.horario = horario;
        aula.coordenadorId = coordenadorIdToken;
        aula.vagasMaximas = requestDTO.vagasMaximas;
        aula.vagasOcupadas = 0;
        aula.ativa = true;
        aula.persist();

        // Vincula cursos autorizados
        for (Long cursoId : requestDTO.cursosAutorizadosIds) {
            Curso curso = Curso.findById(cursoId);
            if (curso == null) throw new BusinessException("Curso não encontrado: " + cursoId);

            AulaCurso ac = new AulaCurso();
            ac.aula = aula;
            ac.curso = curso;
            ac.persist();
        }

        return aula;
    }

    @Transactional
    public Aula atualizar(Long aulaId, AulaUpdateRequestDTO req) {
        UUID coordenadorId = UUID.fromString(jwt.getSubject());


        Aula aula = Aula.findById(aulaId);
        if (aula == null || !aula.ativa) throw new BusinessException("Aula não encontrada");

        if (!aula.coordenadorId.equals(coordenadorId)) {
            throw new BusinessException("Acesso negado para esta aula");
        }

        if (req.professorId != null) {
            Professor p = Professor.findById(req.professorId);
            if (p == null) throw new BusinessException("Professor não encontrado");
            aula.professor = p;
        }

        if (req.disciplinaId != null) {
            Disciplina d = Disciplina.findById(req.disciplinaId);
            if (d == null) throw new BusinessException("Disciplina não encontrada");
            aula.disciplina = d;
        }

        if (req.horarioId != null) {
            Horario h = Horario.findById(req.horarioId);
            if (h == null) throw new BusinessException("Horário não encontrado");

            // Disciplina pode repetir, mas não no mesmo horário
            long existe = Aula.count("disciplina.id = ?1 and horario.id = ?2 and ativa = true and id <> ?3",
                    aula.disciplina.id, req.horarioId, aulaId);
            if (existe > 0) throw new BusinessException("Já existe oferta dessa disciplina nesse horário");

            aula.horario = h;
        }

        if (req.cursosAutorizadosIds != null) {
            long matriculasCount = Matricula.count("aula.id", aulaId);
            if (matriculasCount > 0) {
                throw new BusinessException("Não é permitido alterar aulas com alunos matriculados");
            }

            em.createQuery("delete from AulaCurso ac where ac.aula.id = :aulaId")
                    .setParameter("aulaId", aulaId)
                    .executeUpdate();

            for (Long cursoId : req.cursosAutorizadosIds) {
                Curso c = Curso.findById(cursoId);
                if (c == null) throw new BusinessException("Curso não encontrado: " + cursoId);

                AulaCurso ac = new AulaCurso();
                ac.aula = aula;
                ac.curso = c;
                ac.persist();
            }
        }
        aula.persist();
        return aula;
    }

    @Transactional
    public void excluir(Long aulaId) {
        UUID coordenadorId = UUID.fromString(jwt.getSubject());

        Aula aula = Aula.findById(aulaId);
        if (aula == null) throw new BusinessException("Aula não encontrada");

        if (!aula.coordenadorId.equals(coordenadorId)) {
            throw new BusinessException("Acesso negado para esta aula");
        }

        long matriculas = Matricula.count("aula.id", aulaId);
        if (matriculas > 0) throw new BusinessException("Não é permitido excluir aula com alunos matriculados");

        aula.ativa = false;
    }

    public AulaResponseDTO toResponse(Aula aula) {
        AulaResponseDTO r = new AulaResponseDTO();
        r.id = aula.id;

        r.disciplinaId = aula.disciplina.id;
        r.disciplinaNome = aula.disciplina.nome;

        r.professorId = aula.professor.id;
        r.professorNome = aula.professor.nome;

        r.horarioId = aula.horario.id;
        r.diaSemana = aula.horario.diaSemana;
        r.inicio = aula.horario.inicio.toString();
        r.fim = aula.horario.fim.toString();

        r.vagasMaximas = aula.vagasMaximas;
        r.vagasOcupadas = aula.vagasOcupadas;

        r.ativa = aula.ativa;

        r.cursosAutorizadosIds = cursosAutorizadosIds(aula.id);
        return r;
    }

    private List<Long> cursosAutorizadosIds(Long aulaId) {
        return em.createQuery(
                "select ac.curso.id from AulaCurso ac where ac.aula.id = :aulaId",
                Long.class
        ).setParameter("aulaId", aulaId)
        .getResultList();
    }
}

