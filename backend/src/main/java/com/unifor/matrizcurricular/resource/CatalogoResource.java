package com.unifor.matrizcurricular.resource;

import com.unifor.matrizcurricular.api.dto.CatalogoItemResponseDTO;
import com.unifor.matrizcurricular.api.dto.HorarioResponseDTO;
import com.unifor.matrizcurricular.domain.Curso;
import com.unifor.matrizcurricular.domain.Disciplina;
import com.unifor.matrizcurricular.domain.Horario;
import com.unifor.matrizcurricular.domain.Professor;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("catalogo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"ALUNO","COORDENADOR"}) // catálogo pode ser público; se quiser, troque por @RolesAllowed({"ALUNO","COORDENADOR"})
public class CatalogoResource {

    @GET
    @Path("/disciplinas")
    public List<CatalogoItemResponseDTO> disciplinas() {
        return Disciplina.<Disciplina>listAll().stream()
                .map(d -> new CatalogoItemResponseDTO(d.id, d.nome))
                .toList();
    }

    @GET
    @Path("/professores")
    public List<CatalogoItemResponseDTO> professores() {
        return Professor.<Professor>listAll().stream()
                .map(p -> new CatalogoItemResponseDTO(p.id, p.nome))
                .toList();
    }

    @GET
    @Path("/cursos")
    public List<CatalogoItemResponseDTO> cursos() {
        return Curso.<Curso>listAll().stream()
                .map(c -> new CatalogoItemResponseDTO(c.id, c.nome))
                .toList();
    }

    @GET
    @Path("/horarios")
    public List<HorarioResponseDTO> horarios() {
        return Horario.<Horario>listAll().stream()
                .map(h -> new HorarioResponseDTO(
                        h.id,
                        h.diaSemana.name(),
                        h.inicio.toString(),
                        h.fim.toString()
                ))
                .toList();
    }
}
