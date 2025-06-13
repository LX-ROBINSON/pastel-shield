package org.mysterysolved.appshield.service;

import org.mysterysolved.appshield.common.dto.RequestFormDTO;

public interface AuthService {

    String login(RequestFormDTO tempDTO);

    void logout(int id);
}
