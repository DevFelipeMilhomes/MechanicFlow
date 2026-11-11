package com.devfelipemilhomes.stock.dto;

import com.devfelipemilhomes.part.dto.PartSummaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(name = "Estoque retornado")
public record StockResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "peça_resumida", example = "{id: 2, name: 'Óleo 20W50', unitPrice: 43.50}")
        PartSummaryDTO part,
        @Schema(name = "quantidade_em_mãos", example = "10")
        int quantityOnHand,
        @Schema(name = "quantidade_reservada", example = "3")
        int quantityReserved,
        @Schema(name = "atualizado_em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime updatedAt
) {
}
