package org.mysterysolved.appshield.repository;

import org.mysterysolved.appshield.entity.User;

import java.util.Optional;

public interface AuthRepository {

    Optional<User> findUserByName(String username);

}
