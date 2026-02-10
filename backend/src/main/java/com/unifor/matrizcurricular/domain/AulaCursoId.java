package com.unifor.matrizcurricular.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Access(AccessType.FIELD)
public class AulaCursoId implements Serializable {

    @Column(name = "aula_id")
    public Long aulaId;

    @Column(name = "curso_id")
    public Long cursoId;
}
