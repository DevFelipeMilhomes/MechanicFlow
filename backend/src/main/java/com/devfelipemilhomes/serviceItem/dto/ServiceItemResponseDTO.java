package com.devfelipemilhomes.serviceItem.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ServiceItemResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal basePrice,
        OffsetDateTime createdAt
) {
}
