package com.unifor.matrizcurricular.resource;

import com.unifor.matrizcurricular.api.dto.AulaCreateRequestDTO;
import com.unifor.matrizcurricular.domain.Aula;
import com.unifor.matrizcurricular.service.AulaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;


import java.util.UUID;

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

//    @GET
//    @PermitAll
//    public List<Aula> listar() {
//        return service.listarAtivas();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    @RolesAllowed("COORDENADOR")
//    public void excluir(@PathParam("id") Long id) {
//        service.excluir(id);
//    }
}
