package com.devfelipemilhomes.appUser.dto;

import java.util.List;

public record CurrentUserResponse(
        Long appUserId,
        Long professionalId,
        List<String> roles
) {}
