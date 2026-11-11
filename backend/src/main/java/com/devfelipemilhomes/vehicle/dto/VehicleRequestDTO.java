package com.devfelipemilhomes.vehicle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "Veículo requisitado")
public record VehicleRequestDTO(
        @NotBlank(message = "required field")
        @Size(min = 5, max = 10, message = "The field is outside the 5 to 10 character range.")
        @Pattern(
                regexp = "^[A-Za-z]{3}-?[0-9][A-Za-z0-9]-?[0-9]{2}$",
                message = "Invalid plate"
        )
        @Schema(name = "placa", example = "AAA-0000")
        String plate,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 60, message = "The field is outside the 2 to 60 character range.")
        @Schema(name = "marca", example = "Fiat")
        String brand,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 100, message = "The field is outside the 2 to 100 character range.")
        @Schema(name = "modelo", example = "Mobi 1.0 2025")
        String model,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 150, message = "The field is outside the 2 to 150 character range.")
        @Schema(name = "proprietário", example = "Felipe Barbosa")
        String proprietor
) {
}
