package com.unifor.matrizcurricular.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;


@Entity
@Table(name = "aula_cursos", schema = "matrizcurricular")
@Access(AccessType.FIELD)
public class AulaCurso extends PanacheEntityBase {

    @EmbeddedId
    public AulaCursoId id = new AulaCursoId();

    @ManyToOne(optional = false)
    @MapsId("aulaId")
    @JoinColumn(name = "aula_id")
    public Aula aula;

    @ManyToOne(optional = false)
    @MapsId("cursoId")
    @JoinColumn(name = "curso_id")
    public Curso curso;

}



