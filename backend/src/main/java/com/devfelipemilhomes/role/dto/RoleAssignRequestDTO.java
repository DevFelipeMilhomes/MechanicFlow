package com.devfelipemilhomes.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Função requisitada para associação")
public record RoleAssignRequestDTO(
        @Schema(name = "id", example = "3")
        Long roleId
) {
}
