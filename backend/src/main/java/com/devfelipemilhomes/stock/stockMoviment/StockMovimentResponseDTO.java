package com.devfelipemilhomes.stock.stockMoviment;

import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartSummaryDTO;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record StockMovimentResponseDTO(
        Long id,
        Long stockId,
        @Nullable
        Long serviceOrderPartId,
        String partName,
        BigDecimal unitPrice,
        MovimentType movementType,
        Integer quantity,
        String description,
        OffsetDateTime createdAt
) {
}
