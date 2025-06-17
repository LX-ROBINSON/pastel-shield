package org.mysterysolved.appshield.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.mysterysolved.appshield.config.DataSourceConfig;
import org.mysterysolved.appshield.entity.User;

import java.util.Optional;

@ApplicationScoped
public class AuthRepositoryImpl {

    private DataSourceConfig source;

    public AuthRepositoryImpl() {
    }

    @Inject
    public AuthRepositoryImpl(DataSourceConfig source) {
        this.source = source;
    }

    public Optional<User> findUserByName(String username) {
        Query query = source.getDataSource().createNativeQuery("SELECT * FROM user WHERE username=?", User.class);
        query.setParameter(1, username);
        try {
            User user = (User) query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
