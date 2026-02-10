package com.unifor.matrizcurricular.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class SecurityUtils {

    @Inject
    JsonWebToken jwt;

    public String getUserId() {
        return jwt.getSubject();
    }

    public boolean hasRole(String role) {
        return jwt.getGroups().contains(role);
    }
}
