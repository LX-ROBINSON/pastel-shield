package org.mysterysolved.appshield.service;

import org.mysterysolved.appshield.common.dto.RequestFormDTO;
import org.mysterysolved.appshield.entity.User;

public interface AuthPersistService {

    User createUser(RequestFormDTO tempDTO);

    void removeUser(int id);
}