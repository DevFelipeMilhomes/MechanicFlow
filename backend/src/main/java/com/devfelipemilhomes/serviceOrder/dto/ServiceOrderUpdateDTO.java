package com.devfelipemilhomes.serviceOrder.dto;

import com.devfelipemilhomes.serviceOrder.ServiceOrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceOrderUpdateDTO(
        @NotNull(message = "Required field")
        ServiceOrderStatus status,
        String cancellationReason
) {
}
