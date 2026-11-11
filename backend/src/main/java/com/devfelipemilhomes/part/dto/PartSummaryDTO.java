package com.devfelipemilhomes.part.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "Peça resumida")
public record PartSummaryDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "nome", example = "Óleo 20W50")
        String name,
        @Schema(name = "preço unitário", example = "43.50")
        BigDecimal unitPrice
) {
}
