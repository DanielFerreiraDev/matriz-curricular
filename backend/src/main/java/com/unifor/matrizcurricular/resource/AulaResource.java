package com.unifor.matrizcurricular.resource;

import com.unifor.matrizcurricular.api.dto.AulaCreateRequestDTO;
import com.unifor.matrizcurricular.domain.Aula;
import com.unifor.matrizcurricular.service.AulaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/aulas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AulaResource {

    @Inject AulaService service;

    @POST
    @RolesAllowed("COORDENADOR")
    public Response criar(AulaCreateRequestDTO req) {
        Aula aula = service.criarAula(req);
        return Response.status(Response.Status.CREATED).entity(service.toResponse(aula)).build();
    }

}
