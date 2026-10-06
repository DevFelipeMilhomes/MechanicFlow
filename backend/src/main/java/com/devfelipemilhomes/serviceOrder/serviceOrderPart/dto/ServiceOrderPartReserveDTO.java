package com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceOrderPartReserveDTO(
        @NotNull(message = "Required field")
        Long partId,
        @NotNull(message = "Required field")
        @Positive(message = "The quantity must be greater than zero.")
        Integer quantityReserved
) {
}
