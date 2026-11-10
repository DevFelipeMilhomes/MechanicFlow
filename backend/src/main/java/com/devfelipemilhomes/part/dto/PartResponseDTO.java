package com.devfelipemilhomes.part.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(name = "Peça retornada")
public record PartResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "nome", example = "Óleo 20W50")
        String name,
        @Schema(name = "descrição", example = "Óleo mineral para motores.")
        String description,
        @Schema(name = "preço unitário", example = "43.50")
        BigDecimal unitPrice,
        @Schema(name = "Criado em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime createdAt
) {
}
