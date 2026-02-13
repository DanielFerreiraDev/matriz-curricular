package com.unifor.matrizcurricular.resource;

import com.unifor.matrizcurricular.api.dto.AulaCreateRequestDTO;
import com.unifor.matrizcurricular.api.dto.AulaFiltroParameters;
import com.unifor.matrizcurricular.api.dto.AulaResponseDTO;
import com.unifor.matrizcurricular.api.dto.AulaUpdateRequestDTO;
import com.unifor.matrizcurricular.domain.Aula;
import com.unifor.matrizcurricular.service.AulaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/aulas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AulaResource {

    @Inject AulaService service;

    @GET
    @RolesAllowed("COORDENADOR")
    public List<AulaResponseDTO> listar(
            @QueryParam("periodo") String periodo,
            @QueryParam("diaSemana") String diaSemana,
            @QueryParam("inicio") String inicio,
            @QueryParam("fim") String fim,
            @QueryParam("cursoIds") List<Long> cursoIds,
            @QueryParam("vagasMaximas") Integer vagasMaximas
    ) {
        AulaFiltroParameters aulaFiltroParameters = new AulaFiltroParameters();
        aulaFiltroParameters.periodo = periodo;
        aulaFiltroParameters.diaSemana = diaSemana;
        aulaFiltroParameters.inicio = inicio;
        aulaFiltroParameters.fim = fim;
        aulaFiltroParameters.cursoListIds = cursoIds;
        aulaFiltroParameters.vagasMaximas = vagasMaximas;

        return service.listarAulasDoCoordenador(aulaFiltroParameters);
    }

    @POST
    @RolesAllowed("COORDENADOR")
    public Response criar(AulaCreateRequestDTO req) {
        Aula aula = service.criarAula(req);
        return Response.status(Response.Status.CREATED).entity(service.toResponse(aula)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("COORDENADOR")
    public Response atualizar(@PathParam("id") Long id, AulaUpdateRequestDTO req) {

        Aula aula = service.atualizar(id, req);
        return Response.ok(service.toResponse(aula)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("COORDENADOR")
    public Response excluir(@PathParam("id") Long id) {

        service.excluir(id);
        return Response.noContent().build();
    }

}
