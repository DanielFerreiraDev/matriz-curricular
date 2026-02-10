SET search_path TO matrizcurricular;

CREATE TABLE cursos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE disciplinas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE professores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS horarios (
    id BIGSERIAL PRIMARY KEY,
    dia_semana VARCHAR(10) NOT NULL CHECK (dia_semana IN ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY')),
    inicio TIME NOT NULL,
    fim TIME NOT NULL,
    CONSTRAINT ck_horario_intervalo CHECK (inicio < fim)
);


CREATE TABLE IF NOT EXISTS aulas (
    id BIGSERIAL PRIMARY KEY,

    disciplina_id BIGINT NOT NULL REFERENCES disciplinas(id),
    professor_id  BIGINT NOT NULL REFERENCES professores(id),
    horario_id    BIGINT NOT NULL REFERENCES horarios(id),

    coordenador_id UUID NOT NULL,

    vagas_maximas INT NOT NULL CHECK (vagas_maximas > 0),
    vagas_ocupadas INT NOT NULL DEFAULT 0 CHECK (vagas_ocupadas >= 0),

    ativa BOOLEAN NOT NULL DEFAULT TRUE
);

-- regra: mesma disciplina pode repetir, mas NÃO no mesmo horário
CREATE UNIQUE INDEX IF NOT EXISTS uk_aula_disciplina_horario
ON aulas (disciplina_id, horario_id);

CREATE TABLE IF NOT EXISTS aula_cursos (
    aula_id  BIGINT NOT NULL REFERENCES aulas(id) ON DELETE CASCADE,
    curso_id BIGINT NOT NULL REFERENCES cursos(id),
    PRIMARY KEY (aula_id, curso_id)
);

CREATE TABLE IF NOT EXISTS matriculas (
    id BIGSERIAL PRIMARY KEY,
    aluno_id UUID NOT NULL,
    aula_id  BIGINT NOT NULL REFERENCES aulas(id),

    CONSTRAINT uk_aluno_aula UNIQUE (aluno_id, aula_id)
);
