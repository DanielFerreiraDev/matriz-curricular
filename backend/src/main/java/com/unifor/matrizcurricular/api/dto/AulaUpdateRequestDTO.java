package com.unifor.matrizcurricular.api.dto;

import java.util.List;

public class AulaUpdateRequestDTO {

    public Long disciplinaId;
    public Long professorId;
    public Long horarioId;
    public List<Long> cursosAutorizadosIds;
}