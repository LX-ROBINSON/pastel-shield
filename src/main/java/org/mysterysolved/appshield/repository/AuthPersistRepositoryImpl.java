package org.mysterysolved.appshield.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mysterysolved.appshield.config.DataSourceConfig;
import org.mysterysolved.appshield.entity.User;

@ApplicationScoped
public class AuthPersistRepositoryImpl {

    private DataSourceConfig dataSource;

    public AuthPersistRepositoryImpl() {
    }

    @Inject
    public AuthPersistRepositoryImpl(DataSourceConfig dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public User createUser(User tempUser) {
        dataSource.getDataSource().persist(tempUser);
        return tempUser;
    }

    @Transactional
    public void removeUser(int id) {
        User proxy = dataSource.getDataSource().getReference(User.class, id);
        dataSource.getDataSource().remove(proxy);
    }

    @Transactional
    public User updateUser(User user) {
        return dataSource.getDataSource().merge(user);
    }
}
