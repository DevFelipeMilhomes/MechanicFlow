package com.devfelipemilhomes.client.dto;

import java.time.OffsetDateTime;

public record ClientResponseDTO(
        Long id,
        String name,
        String cpf,
        String phone,
        String email,
        OffsetDateTime createdAt
) {
}
