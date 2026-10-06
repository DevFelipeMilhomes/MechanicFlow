package com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceOrderPartReleaseDTO(
        @NotNull(message = "Required field")
        Long ServiceOrderPartId,
        @Positive(message = "The quantity must be greater than zero.")
        Integer quantityRelease
) {
}
