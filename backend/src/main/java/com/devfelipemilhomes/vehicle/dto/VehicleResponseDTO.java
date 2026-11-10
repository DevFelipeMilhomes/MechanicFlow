package com.devfelipemilhomes.vehicle.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Veículo retornado")
public record VehicleResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "placa", example = "AAA-0000")
        String plate,
        @Schema(name = "marca", example = "Fiat")
        String brand,
        @Schema(name = "modelo", example = "Mobi 1.0 2025")
        String model,
        @Schema(name = "proprietário", example = "Felipe Barbosa")
        String proprietor
) {
}
