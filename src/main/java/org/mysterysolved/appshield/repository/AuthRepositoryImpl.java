package org.mysterysolved.appshield.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.mysterysolved.appshield.entity.User;

import java.util.Optional;

@ApplicationScoped
public class AuthRepositoryImpl implements AuthRepository {

    @PersistenceContext(unitName = "jdbc/pastel_shield")
    private EntityManager em;

    @Override
    public Optional<User> findUserByName(String username) {
        Query query = em.createNativeQuery("SELECT * FROM user WHERE username=?", User.class);
        query.setParameter(1, username);
        try {
            User user = (User) query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
