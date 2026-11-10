package com.devfelipemilhomes.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Função requisitada")
public record RoleRequestDTO(
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 50, message = "The field is outside the 2 to 50 character range.")
        @Schema(name = "nome", example = "GERENTE")
        String name,
        @Size(min = 2, max = 255, message = "The field is outside the 1 to 255 character range.")
        @Schema(name = "descrição", example = "Gerenciamento geral do sistema")
        String description
) {
}
