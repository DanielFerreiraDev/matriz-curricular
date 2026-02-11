package com.unifor.matrizcurricular.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(
    name = "matriculas",
    uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "aula_id"})
)
public class Matricula extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "aluno_id", nullable = false)
    public UUID alunoId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aula_id")
    public Aula aula;
}

