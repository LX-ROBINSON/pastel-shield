package org.mysterysolved.appshield.common.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ErrorResponseExceptionMapper implements ExceptionMapper<InvalidDataException> {

    @Override
    public Response toResponse(InvalidDataException e) {
        return Response.status(e.getCode())
                .entity(e.getMessage())
                .build();
    }
}
