package com.unifor.matrizcurricular.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AulaCursoId implements Serializable {

    @Column(name = "aula_id")
    public Long aulaId;

    @Column(name = "curso_id")
    public Long cursoId;

    public AulaCursoId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AulaCursoId that = (AulaCursoId) o;
        return java.util.Objects.equals(aulaId, that.aulaId) &&
                java.util.Objects.equals(cursoId, that.cursoId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(aulaId, cursoId);
    }
}
