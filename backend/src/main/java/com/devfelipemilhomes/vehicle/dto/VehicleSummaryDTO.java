package com.devfelipemilhomes.vehicle.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Veículo resumido")
public record VehicleSummaryDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "placa", example = "AAA-0000")
        String plate,
        @Schema(name = "modelo", example = "Mobi 1.0 2025")
        String model
) {
}
