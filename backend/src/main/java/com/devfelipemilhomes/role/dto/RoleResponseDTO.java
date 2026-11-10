package com.devfelipemilhomes.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Função retornada")
public record RoleResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "nome", example = "GERENTE")
        String name,
        @Schema(name = "descrição", example = "Gerenciamento geral do sistema")
        String description
) {
}
