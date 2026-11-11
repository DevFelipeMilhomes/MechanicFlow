package com.devfelipemilhomes.appUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(name = "Usuário retornado")
public record UserResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "username", example = "felipe")
        String username,
        @Schema(name = "ativo", example = "true")
        boolean enabled,
        @Schema(name = "id_do_profissional", example = "4")
        Long professionalId,
        @Schema(name = "email", example = "usuario@gmail.com")
        String email,
        @Schema(name = "lista_de_funções", example = "['GERENTE', 'MECANICO']")
        List<String> roles,
        @Schema(name = "criado_em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime createdAt
) {
}
