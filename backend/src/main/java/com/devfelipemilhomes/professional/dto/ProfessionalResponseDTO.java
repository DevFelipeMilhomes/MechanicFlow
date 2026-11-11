package com.devfelipemilhomes.professional.dto;

import com.devfelipemilhomes.role.dto.RoleSummaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Set;

@Schema(name = "Profissional retornado")
public record ProfessionalResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "nome", example = "Felipe Barbosa")
        String name,
        @Schema(name = "cpf", example = "12345678910")
        String cpf,
        @Schema(name = "telefone", example = "88988888888")
        String phone,
        @Schema(name = "email", example = "usuario@gmail.com")
        String email,
        @Schema(name = "lista resumida de funções", example = "[{id:2, name: GERENTE}]")
        Set<RoleSummaryDTO> roles,
        @Schema(name = "Criado em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime createdAt
) {
}
