package com.devfelipemilhomes.part.dto;

import java.math.BigDecimal;

public record PartSummaryDTO(
        Long id,
        String name,
        BigDecimal unitPrice
) {
}
