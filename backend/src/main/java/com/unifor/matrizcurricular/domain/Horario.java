package com.unifor.matrizcurricular.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "horarios")
public class Horario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "dia_semana", nullable = false)
    public String diaSemana; // MONDAY...

    @Column(nullable = false)
    public LocalTime inicio;

    @Column(nullable = false)
    public LocalTime fim;
}
