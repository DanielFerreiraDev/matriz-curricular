package com.unifor.matrizcurricular.api.dto;

import java.util.List;

public class AulaResponseDTO {
    public Long id;

    public Long disciplinaId;
    public String disciplinaNome;

    public Long professorId;
    public String professorNome;

    public Long horarioId;
    public String diaSemana;
    public String inicio;
    public String fim;

    public Integer vagasMaximas;
    public Integer vagasOcupadas;

    public boolean ativa;

    public List<Long> cursosAutorizadosIds;
}

