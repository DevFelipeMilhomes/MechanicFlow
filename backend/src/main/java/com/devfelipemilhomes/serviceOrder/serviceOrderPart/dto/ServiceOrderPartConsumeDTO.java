package com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceOrderPartConsumeDTO(
        @NotNull(message = "Required field")
        Long ServiceOrderPartId,
        @NotNull(message = "Required field")
        @Positive(message = "The quantity must be greater than zero.")
        Integer quantityConsume
) {
}
