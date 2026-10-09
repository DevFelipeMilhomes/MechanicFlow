package com.devfelipemilhomes.appUser.dto;

import com.devfelipemilhomes.professional.Professional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Required field")
        @Size(min = 3, max = 50, message = "The field is outside the 3 to 50 character range.")
        String username,
        @NotBlank(message = "Required field")
        @Size(min = 8, max = 64, message = "The field is outside the 8 to 64 character range.")
        String password,
        @NotNull(message = "Required field")
        Long ProfessionalId
) {
}
