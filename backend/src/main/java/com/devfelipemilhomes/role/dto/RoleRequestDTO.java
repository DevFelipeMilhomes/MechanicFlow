package com.devfelipemilhomes.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestDTO(
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 50, message = "The field is outside the 2 to 50 character range.")
        String name,
        @Size(min = 2, max = 255, message = "The field is outside the 1 to 255 character range.")
        String description
) {
}
