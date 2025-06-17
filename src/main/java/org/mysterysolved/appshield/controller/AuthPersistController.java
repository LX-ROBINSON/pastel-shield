package org.mysterysolved.appshield.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.entity.User;
import org.mysterysolved.appshield.service.AuthPersistServiceImpl;

import java.net.URI;
import java.util.logging.Logger;

@Path("persist")
@Produces(MediaType.APPLICATION_JSON)
public class AuthPersistController {

    @Context
    private UriInfo uriInfo;

    private final AuthPersistServiceImpl service;

    static private final Logger lg = Logger.getLogger(AuthPersistController.class.getName());

    @Inject
    public AuthPersistController(AuthPersistServiceImpl service) {
        this.service = service;
    }

    @POST
    @Path("register-user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid RequestFormDTO formDTO) {
        User tempUser = service.createUser(formDTO);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(tempUser.getId())).build();

        return Response.created(uri)
                .entity(tempUser)
                .build();
    }

    @DELETE
    @Path("remove-user/{id}")
    public Response removeUser(@PathParam("id") @Min(value = 1, message = "Please enter a valid value") int idUser) {
        service.removeUser(idUser);
        return Response.ok().build();
    }

    @PUT
    @Path("update-user")
    public Response updateUser(@Valid RequestFormDTO formDTO) {
        User tempUser = service.updateUser(formDTO);
        return Response.ok()
                .entity(tempUser)
                .build();
    }
}
