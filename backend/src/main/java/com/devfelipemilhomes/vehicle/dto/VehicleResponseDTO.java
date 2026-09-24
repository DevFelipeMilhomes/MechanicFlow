package com.devfelipemilhomes.vehicle.dto;

public record VehicleResponseDTO(
        Long id,
        String plate,
        String bland,
        String model,
        String proprietor
) {
}
