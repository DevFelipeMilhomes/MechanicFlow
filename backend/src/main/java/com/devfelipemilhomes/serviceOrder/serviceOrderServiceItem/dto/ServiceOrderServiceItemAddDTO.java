package com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto;

import jakarta.validation.constraints.NotNull;

public record ServiceOrderServiceItemAddDTO(
        @NotNull(message = "Required field")
        Long serviceItemId
) {
}
