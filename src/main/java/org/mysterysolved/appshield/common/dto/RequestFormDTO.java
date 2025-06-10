package org.mysterysolved.appshield.common.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestFormDTO(@NotBlank(message = "The name must not be empty") String username,
                             @NotBlank(message = "The password must not be empty") String password) {
}
