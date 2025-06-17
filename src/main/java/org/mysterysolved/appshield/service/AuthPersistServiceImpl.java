package org.mysterysolved.appshield.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.Response;
import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.common.exception.InvalidDataException;
import org.mysterysolved.appshield.entity.User;
import org.mysterysolved.appshield.repository.AuthPersistRepositoryImpl;
import org.mysterysolved.appshield.repository.AuthRepositoryImpl;
import org.mysterysolved.appshield.security.BcryptPassword;

@ApplicationScoped
public class AuthPersistServiceImpl {

    private AuthPersistRepositoryImpl persistRepository;
    private BcryptPassword bcrypt;
    private AuthRepositoryImpl authRepository;

    public AuthPersistServiceImpl() {
    }

    @Inject
    public AuthPersistServiceImpl(AuthRepositoryImpl authRepository, AuthPersistRepositoryImpl persistRepository, BcryptPassword bcrypt) {
        this.persistRepository = persistRepository;
        this.authRepository = authRepository;
        this.bcrypt = bcrypt;
    }

    public User createUser(RequestFormDTO requestDTO) {
        String tempUsername = requestDTO.username();

        authRepository.findUserByName(tempUsername)
                .ifPresent(user -> {
                    throw new InvalidDataException("The name "+ user.getUsername() + " already exists",
                            Response.Status.BAD_REQUEST.getStatusCode());
                });

        String textBcrypt = generateJwt(requestDTO);
        User tempUser = new User(tempUsername, textBcrypt);

        return persistRepository.createUser(tempUser);
    }

    public User updateUser(RequestFormDTO requestDTO) {
        String tempUsername = requestDTO.username();

        User user = authRepository.findUserByName(tempUsername).orElseThrow(() ->
                new InvalidDataException("The entered user does not exist", Response.Status.BAD_REQUEST.getStatusCode())
        );
        user.setPassword(requestDTO.password());
        return persistRepository.updateUser(user);
    }

    public void removeUser(int id) {
        persistRepository.removeUser(id);
    }

    private String generateJwt(RequestFormDTO requestDTO) {
        return bcrypt.generate(requestDTO.password().toCharArray());
    }


}
