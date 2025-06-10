package org.mysterysolved.appshield.filter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.mysterysolved.appshield.common.jwt.JwtUtils;

import java.awt.image.Kernel;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = "")
public class AccessResourceFilter implements Filter {

    private final JwtUtils jwtUtils;

    @Inject
    public AccessResourceFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        Optional<DecodedJWT> opVerifier = jwtUtils.isTokenIsValid(token);

        if (opVerifier.isPresent()) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
