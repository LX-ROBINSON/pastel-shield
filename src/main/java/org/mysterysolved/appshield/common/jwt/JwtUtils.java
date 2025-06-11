package org.mysterysolved.appshield.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.enterprise.context.RequestScoped;
import org.mysterysolved.appshield.entity.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class JwtUtils {

    private static final String SECRET_KEY = "ccbec431b0bc6ea74d008b03238f3462aaf94bee3439a4d944f0aaa44b742701";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM)
            .withIssuer("mystery-resolved")
            .build();


    public String generateJWT(User user) {
        return JWT.create()
                .withIssuer("mystery-resolved")
                .withSubject("Login JWT")
                .withClaim("idUser", user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.MINUTES)))
                .withJWTId(UUID.randomUUID().toString())
                .sign(ALGORITHM);
    }

    public Optional<DecodedJWT> isTokenIsValid(String token) {
        if (token == null || token.isBlank()) return Optional.empty();

        try {
            return Optional.of(VERIFIER.verify(token));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
