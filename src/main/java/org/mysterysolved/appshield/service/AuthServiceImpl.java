package org.mysterysolved.appshield.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.common.exception.InvalidDataException;
import org.mysterysolved.appshield.common.jwt.JwtUtils;
import org.mysterysolved.appshield.entity.User;
import org.mysterysolved.appshield.repository.AuthRepository;
import org.mysterysolved.appshield.security.BcryptPassword;

import java.util.Optional;

@RequestScoped
public class AuthServiceImpl implements AuthService {

    private final AuthRepository repository;
    private final BcryptPassword bcrypt;
    private final JwtUtils jwt;

    @Inject
    public AuthServiceImpl(AuthRepository repository, BcryptPassword bcrypt, JwtUtils jwt) {
        this.repository = repository;
        this.bcrypt = bcrypt;
        this.jwt = jwt;
    }

    @Override
    public String login(RequestFormDTO tempDTO) {
        Optional<User> opUser = repository.findUserByName(tempDTO.username());

        if (opUser.isPresent()) {
            User user = opUser.get();
            if (bcrypt.verify(tempDTO.password().toCharArray(), user.getPassword()))
                return jwt.generateJWT(user);
        }
        throw new InvalidDataException("This data does not correspond to any user", Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Override
    public void logout(int id) {

    }
}
