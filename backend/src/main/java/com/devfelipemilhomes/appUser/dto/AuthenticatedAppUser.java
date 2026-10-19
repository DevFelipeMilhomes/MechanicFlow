package com.devfelipemilhomes.appUser.dto;

import java.util.List;

public record AuthenticatedAppUser(
        Long userId,
        Long professionalId,
        List<String> roles
) {
}
