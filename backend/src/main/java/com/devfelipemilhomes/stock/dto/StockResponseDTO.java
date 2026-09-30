package com.devfelipemilhomes.stock.dto;

import com.devfelipemilhomes.part.dto.PartSummaryDTO;

import java.time.OffsetDateTime;

public record StockResponseDTO(
        Long id,
        PartSummaryDTO part,
        int quantityOnHand,
        int quantityReserved,
        OffsetDateTime updatedAt
) {
}
