SET search_path TO matrizcurricular;


CREATE TABLE alunos (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE coordenadores (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);


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

CREATE TABLE horarios (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);


CREATE TABLE aulas (
    id BIGSERIAL PRIMARY KEY,

    disciplina_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    horario_id BIGINT NOT NULL,
    coordenador_id UUID NOT NULL,

    vagas_maximas INT NOT NULL CHECK (vagas_maximas > 0),
    vagas_ocupadas INT NOT NULL DEFAULT 0 CHECK (vagas_ocupadas >= 0),

    ativa BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_aula_disciplina
    FOREIGN KEY (disciplina_id) REFERENCES disciplinas (id),

    CONSTRAINT fk_aula_professor
    FOREIGN KEY (professor_id) REFERENCES professores (id),

    CONSTRAINT fk_aula_horario
    FOREIGN KEY (horario_id) REFERENCES horarios (id),

    CONSTRAINT fk_aula_coordenador
    FOREIGN KEY (coordenador_id) REFERENCES coordenadores (id)
);


CREATE TABLE matriculas (
    id BIGSERIAL PRIMARY KEY,

    aluno_id UUID NOT NULL,
    aula_id BIGINT NOT NULL,

    CONSTRAINT fk_matricula_aluno
    FOREIGN KEY (aluno_id) REFERENCES alunos (id),

    CONSTRAINT fk_matricula_aula
    FOREIGN KEY (aula_id) REFERENCES aulas (id),

    CONSTRAINT uk_aluno_aula UNIQUE (aluno_id, aula_id)
);

CREATE TABLE aula_cursos (
    aula_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,

    PRIMARY KEY (aula_id, curso_id),

    CONSTRAINT fk_aula_curso_aula
    FOREIGN KEY (aula_id) REFERENCES aulas (id)
     ON DELETE CASCADE,

    CONSTRAINT fk_aula_curso_curso
    FOREIGN KEY (curso_id) REFERENCES cursos (id)
);


