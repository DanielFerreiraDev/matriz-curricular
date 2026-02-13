package com.unifor.matrizcurricular.api.dto;

import java.util.List;

public class AulaFiltroParameters {
    public String periodo;
    public String diaSemana;
    public String inicio;
    public String fim;
    public List<Long> cursoListIds;
    public Integer vagasMaximas;
}
