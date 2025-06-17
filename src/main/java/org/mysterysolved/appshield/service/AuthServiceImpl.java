package org.mysterysolved.appshield.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.common.exception.InvalidDataException;
import org.mysterysolved.appshield.common.jwt.JwtUtils;
import org.mysterysolved.appshield.entity.User;
import org.mysterysolved.appshield.repository.AuthRepositoryImpl;
import org.mysterysolved.appshield.security.BcryptPassword;

import java.util.Optional;

@ApplicationScoped
public class AuthServiceImpl {

    private AuthRepositoryImpl repository;
    private BcryptPassword bcrypt;
    private JwtUtils jwt;

    public AuthServiceImpl() {
    }

    @Inject
    public AuthServiceImpl(AuthRepositoryImpl repository, BcryptPassword bcrypt, JwtUtils jwt) {
        this.repository = repository;
        this.bcrypt = bcrypt;
        this.jwt = jwt;
    }

    public String login(RequestFormDTO tempDTO) {
        Optional<User> opUser = repository.findUserByName(tempDTO.username());

        if (opUser.isPresent()) {
            User user = opUser.get();
            if (bcrypt.verify(tempDTO.password().toCharArray(), user.getPassword()))
                return jwt.generateJWT(user);
        }
        throw new InvalidDataException("This data does not correspond to any user", Response.Status.BAD_REQUEST.getStatusCode());
    }
}
