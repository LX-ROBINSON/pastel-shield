package org.mysterysolved.appshield.repository;

import org.mysterysolved.appshield.entity.User;

public interface AuthPersistRepository {

    User createUser(User tempUser);

    void removeUser(int id);

}
