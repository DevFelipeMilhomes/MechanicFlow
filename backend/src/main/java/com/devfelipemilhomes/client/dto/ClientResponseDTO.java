package com.devfelipemilhomes.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(name = "Cliente retornado")
public record ClientResponseDTO(
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
        @Schema(name = "Criado em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime createdAt
) {
}
