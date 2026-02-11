package com.unifor.matrizcurricular.resource;

import com.unifor.matrizcurricular.service.MatriculaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/matriculas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MatriculaResource {

    @Inject MatriculaService service;

    @POST
    @Path("/aula/{aulaId}")
    @RolesAllowed("ALUNO")
    public Response matricular(@PathParam("aulaId") Long aulaId) {
        service.matricular(aulaId);
        return Response.status(Response.Status.CREATED).build();
    }
}
