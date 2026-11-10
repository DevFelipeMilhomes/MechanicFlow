package com.devfelipemilhomes.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Função resumida")
public record RoleSummaryDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "nome", example = "GERENTE")
        String name
) {
}
