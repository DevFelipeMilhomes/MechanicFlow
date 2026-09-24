package com.devfelipemilhomes.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VehicleRequestDTO(
        @NotBlank(message = "required field")
        @Size(min = 5, max = 10, message = "The field is outside the 5 to 10 character range.")
        @Pattern(
                regexp = "^[A-Za-z]{3}-?[0-9][A-Za-z0-9]-?[0-9]{2}$",
                message = "Invalid plate"
        )
        String plate,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 60, message = "The field is outside the 2 to 60 character range.")
        String brand,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 100, message = "The field is outside the 2 to 100 character range.")
        String model,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 150, message = "The field is outside the 2 to 150 character range.")
        String proprietor
) {
}
