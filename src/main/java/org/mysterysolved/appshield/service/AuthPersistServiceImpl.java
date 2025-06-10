package org.mysterysolved.appshield.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.common.exception.InvalidDataException;
import org.mysterysolved.appshield.entity.User;
import org.mysterysolved.appshield.repository.AuthPersistRepository;
import org.mysterysolved.appshield.repository.AuthRepository;
import org.mysterysolved.appshield.security.BcryptPassword;

@ApplicationScoped
public class AuthPersistServiceImpl implements AuthPersistService {

    private final AuthPersistRepository repository;
    private final BcryptPassword bcrypt;
    private final AuthRepository authRepository;

    @Inject
    public AuthPersistServiceImpl(AuthPersistRepository repository, AuthRepository authRepository, BcryptPassword bcrypt) {
        this.bcrypt = bcrypt;
        this.repository = repository;
        this.authRepository = authRepository;
    }

    @Override
    public User createUser(RequestFormDTO requestDTO) {
        String tempUsername = requestDTO.username();

        authRepository.findUserByName(tempUsername)
                .ifPresent(user -> {
                    throw new InvalidDataException("The name "+ user.getUsername() + " already exists",
                            Response.Status.BAD_REQUEST.getStatusCode());
                });

        String textBcrypt = bcrypt.generate(requestDTO.password().toCharArray());
        User tempUser = new User(tempUsername, textBcrypt);

        return repository.createUser(tempUser);
    }

    @Override
    public void removeUser(int id) {
        repository.removeUser(id);
    }
}
