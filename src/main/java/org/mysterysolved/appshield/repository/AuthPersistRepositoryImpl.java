package org.mysterysolved.appshield.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.mysterysolved.appshield.entity.User;

@ApplicationScoped
public class AuthPersistRepositoryImpl implements AuthPersistRepository {

    @PersistenceContext(unitName = "jdbc/pastel_shield")
    private EntityManager em;

    @Override
    @Transactional
    public User createUser(User tempUser) {
        em.persist(tempUser);
        return tempUser;
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        User proxy = em.getReference(User.class, id);
        em.remove(proxy);
    }
}
