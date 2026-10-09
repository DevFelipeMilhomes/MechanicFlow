package com.devfelipemilhomes.appUser.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record UserResponseDTO(
        Long id,
        String username,
        boolean enabled,
        Long professionalId,
        List<String> roles,
        OffsetDateTime created
) {
}
