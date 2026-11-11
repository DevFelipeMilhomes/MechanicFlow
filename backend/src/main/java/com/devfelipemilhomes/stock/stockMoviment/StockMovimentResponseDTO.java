package com.devfelipemilhomes.stock.stockMoviment;

import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartSummaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(name = "Movimento de estoque retornado")
public record StockMovimentResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "estoque_id", example = "2")
        Long stockId,
        @Nullable
        @Schema(name = "peça_de_ordem_de_serviço_id", example = "5")
        Long serviceOrderPartId,
        @Schema(name = "nome_da_peça", example = "Óleo 20W50")
        String partName,
        @Schema(name = "preço_unitário", example = "43.50")
        BigDecimal unitPrice,
        @Schema(name = "tipo_de_movimento", example = "ENTRY")
        MovimentType movementType,
        @Schema(name = "quantidade", example = "3")
        Integer quantity,
        @Schema(name = "descrição", example = "Entry from the stock of 'Óleo 20W50'.")
        String description,
        @Schema(name = "Criado em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime createdAt
) {
}
