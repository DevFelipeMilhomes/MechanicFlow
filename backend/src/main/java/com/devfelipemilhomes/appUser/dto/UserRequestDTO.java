package com.devfelipemilhomes.appUser.dto;

import com.devfelipemilhomes.professional.Professional;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Usuário requisitado")
public record UserRequestDTO(
        @NotBlank(message = "Required field")
        @Size(min = 3, max = 50, message = "The field is outside the 3 to 50 character range.")
        @Schema(name = "username", example = "felipe")
        String username,
        @NotBlank(message = "Required field")
        @Size(min = 8, max = 64, message = "The field is outside the 8 to 64 character range.")
        @Schema(name = "senha")
        String password,
        @NotNull(message = "Required field")
        @Schema(name = "id_do_profissional", example = "4")
        Long ProfessionalId,
        @NotBlank(message = "Required field")
        @Email(message = "Invalid email")
        @Size(min = 1, max = 254, message = "The field is outside the 1 to 254 character range.")
        @Schema(name = "email", example = "usuario@gmail.com")
        String email
) {
}
