package com.unifor.matrizcurricular.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.security.Authenticated;

@Path("/v1/matriz")
public class MatrizResourceHealth {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String publico() {
        return "Este endpoint é público (se não houver segurança global).";
    }

    @GET
    @Path("/protegido")
    @Authenticated
    public String protegido() {
        return "Autenticação com sucesso pelo Keycloak!";
    }

    @GET
    @Path("/aluno")
    @RolesAllowed("ALUNO")
    public String somenteAlunos() {
        return "Olá, Aluno da Matriz Curricular!";
    }

    @GET
    @Path("/coordenador")
    @RolesAllowed("COORDENADOR")
    public String somenteCoordenadores() {
        return "Olá, Coordenador da Matriz Curricular!";
    }
}