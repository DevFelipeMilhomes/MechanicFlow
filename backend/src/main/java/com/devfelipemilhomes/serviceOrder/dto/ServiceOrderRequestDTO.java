package com.devfelipemilhomes.serviceOrder.dto;

import com.devfelipemilhomes.serviceOrder.ServiceOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(name = "Ordem de serviço requisitada")
public record ServiceOrderRequestDTO(
        @NotNull(message = "Required field")
        @Schema(name = "veículo_id", example = "3")
        Long vehicleId,
        @NotNull(message = "Required field")
        @Schema(name = "cliente_id", example = "5")
        Long clientId,
        @NotBlank(message = "Required field")
        @Schema(name = "problema_relatado", example = "Óleo baixo e escuro")
        String reportedProblem,
        @Schema(name = "diagnóstico", example = "Trocar óleo")
        String diagnosis,
        @NotNull(message = "Required field")
        @PositiveOrZero(message = "The odometer must be greater than or equal to zero.")
        @Schema(name = "odômetro", example = "124004")
        Integer odometer
) {
}
