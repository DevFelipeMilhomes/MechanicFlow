package com.devfelipemilhomes.serviceOrder.dto;

import com.devfelipemilhomes.serviceOrder.ServiceOrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ServiceOrderRequestDTO(
        @NotNull(message = "Required field")
        Long vehicleId,
        @NotNull(message = "Required field")
        Long clientId,
        @NotNull(message = "Required field")
        Long professionalId,
        @NotBlank(message = "Required field")
        String reportedProblem,
        String diagnosis,
        @NotNull(message = "Required field")
        @PositiveOrZero(message = "The odometer must be greater than or equal to zero.")
        Integer odometer
) {
}
