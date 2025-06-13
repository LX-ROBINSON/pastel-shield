package org.mysterysolved.appshield.filter;

import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Provider
@PreMatching // Indica que este filtro se tiene que ejecutar primero
@Singleton
public class ConvertMethodHttpFilter implements ContainerRequestFilter {

    private static final Map<String, String> methodsHttp = new HashMap<>();

    static {
        methodsHttp.put("remove-user", "DELETE");
        methodsHttp.put("update-user", "PUT");
    }

    // DELETE REMOVE-USER/2
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String path = containerRequestContext.getUriInfo().getPath(); // persist/remove-user
        String methodHttp = containerRequestContext.getMethod(); // POST

        for (var entry : methodsHttp.entrySet()) {
            if (path.contains(entry.getKey()) && "POST".equalsIgnoreCase(methodHttp)) {
                containerRequestContext.setMethod(entry.getValue());
                break;
            }
        }
    }
}