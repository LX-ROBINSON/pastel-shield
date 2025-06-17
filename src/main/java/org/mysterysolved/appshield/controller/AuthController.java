package org.mysterysolved.appshield.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.service.AuthServiceImpl;

@Path("auth")
public class AuthController {

    private final AuthServiceImpl service;

    @Inject
    public AuthController(AuthServiceImpl service) {
        this.service = service;
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid RequestFormDTO formDTO) {
        String JWT = service.login(formDTO);

        return Response
                .ok(JWT)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
