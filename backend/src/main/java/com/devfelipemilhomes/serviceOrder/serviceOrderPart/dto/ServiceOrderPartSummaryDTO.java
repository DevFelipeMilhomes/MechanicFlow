package com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto;

import java.math.BigDecimal;

public record ServiceOrderPartSummaryDTO(
        Long id,
        Long partId,
        String partName,
        Integer quantityReserved,
        Integer quantityUsed,
        BigDecimal unitPrice
) {
}
