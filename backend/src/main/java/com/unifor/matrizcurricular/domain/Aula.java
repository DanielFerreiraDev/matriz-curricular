package com.unifor.matrizcurricular.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "aulas")
public class Aula extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    public Disciplina disciplina;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id")
    public Professor professor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "horario_id")
    public Horario horario;

    @Column(name = "coordenador_id", nullable = false)
    public UUID coordenadorId;

    @Column(name = "vagas_maximas", nullable = false)
    public int vagasMaximas;

    @Column(name = "vagas_ocupadas", nullable = false)
    public int vagasOcupadas = 0;

    @Column(nullable = false)
    public boolean ativa = true;
}

