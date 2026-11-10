package com.devfelipemilhomes.professional.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProfessionalSummaryDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "nome", example = "Felipe Barbosa")
        String name,
        @Schema(name = "cpf", example = "12345678910")
        String cpf,
        @Schema(name = "telefone", example = "88988888888")
        String phone
) {
}
