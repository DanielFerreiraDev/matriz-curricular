package com.unifor.matrizcurricular.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", ex.getMessage()))
                .build();
    }
}
