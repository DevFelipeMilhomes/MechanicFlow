package com.devfelipemilhomes.vehicle.dto;

public record VehicleResponseDTO(
        Long id,
        String plate,
        String brand,
        String model,
        String proprietor
) {
}
