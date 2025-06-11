package org.mysterysolved.appshield.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.mysterysolved.appshield.common.jwt.JwtUtils;

import java.io.IOException;
import java.util.Optional;

@Provider
public class AccessResourceFilter implements ContainerRequestFilter {

    private final JwtUtils jwtUtils;

    @Inject
    public AccessResourceFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.startsWith("auth")) {
            return;
        }

        String header = requestContext.getHeaderString("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring("Bearer ".length());
            Optional<DecodedJWT> opJwt = jwtUtils.isTokenIsValid(token);

            if (opJwt.isPresent())
                return;
        }
        requestContext.abortWith(
                Response.status(Response.Status.FORBIDDEN)
                        .entity("Token Invalid")
                        .build()
        );
    }
}
