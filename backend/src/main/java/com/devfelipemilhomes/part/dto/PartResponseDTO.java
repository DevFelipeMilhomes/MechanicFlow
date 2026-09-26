package com.devfelipemilhomes.part.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PartResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal unitPrice,
        OffsetDateTime createdAt
) {
}
