package com.devfelipemilhomes.professional.dto;

import com.devfelipemilhomes.role.dto.RoleSummaryDTO;

import java.time.OffsetDateTime;
import java.util.Set;

public record ProfessionalResponseDTO(
        Long id,
        String name,
        String cpf,
        String phone,
        String email,
        Set<RoleSummaryDTO> roles,
        OffsetDateTime createdAt
) {
}
