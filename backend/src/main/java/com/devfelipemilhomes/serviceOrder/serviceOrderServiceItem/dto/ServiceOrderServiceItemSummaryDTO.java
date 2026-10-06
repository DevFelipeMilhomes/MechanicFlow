package com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto;

import java.math.BigDecimal;

public record ServiceOrderServiceItemSummaryDTO(
        Long serviceItemId,
        String serviceItemName,
        BigDecimal unitprice
) {
}
