package org.mysterysolved.appshield.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.common.exception.InvalidDataException;
import org.mysterysolved.appshield.entity.User;
import org.mysterysolved.appshield.repository.AuthRepository;
import org.mysterysolved.appshield.security.BcryptPassword;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class AuthServiceImpl implements AuthService {

    private final AuthRepository repository;
    private final BcryptPassword bcrypt;

    @Inject
    public AuthServiceImpl(AuthRepository repository, BcryptPassword bcrypt) {
        this.repository = repository;
        this.bcrypt = bcrypt;
    }

    @Override
    public String login(RequestFormDTO tempDTO) {
        Optional<User> opUser = repository.findUserByName(tempDTO.username());

        if (opUser.isPresent()) {
            User user = opUser.get();
            if (bcrypt.verify(tempDTO.password().toCharArray(), user.getPassword()))
                return generateJWT(user);
        }
        throw new InvalidDataException("This data does not correspond to any user", Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Override
    public void logout(int id) {

    }

    private String generateJWT(User user) {
        String nameUser = user.getUsername();

        Algorithm algorithm = Algorithm.HMAC256("12345");

        return JWT.create()
                .withIssuer(nameUser)
                .withSubject("Login JWT")
                .withClaim("id", user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 500L))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 100L))
                .sign(algorithm);
    }
}
