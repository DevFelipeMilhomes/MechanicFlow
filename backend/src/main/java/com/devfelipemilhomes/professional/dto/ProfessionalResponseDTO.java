package com.devfelipemilhomes.professional.dto;

import java.time.OffsetDateTime;

public record ProfessionalResponseDTO(
        Long id,
        String name,
        String cpf,
        String phone,
        String email,
        OffsetDateTime createdAt
) {
}
