package com.devfelipemilhomes.serviceItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(name = "Serviço retornado")
public record ServiceItemResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "nome", example = "Troca de filtros")
        String name,
        @Schema(name = "descrição", example = "Troca de filtros de ar, combustivel ou óleo")
        String description,
        @Schema(name = "preço_base", example = "20.00")
        BigDecimal basePrice,
        @Schema(name = "Criado em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime createdAt
) {
}
