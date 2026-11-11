package com.devfelipemilhomes.client.dto;

import com.devfelipemilhomes.validation.ValidCpf;
import com.devfelipemilhomes.validation.ValidPhone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Cliente requisitado")
public record ClientRequestDTO(
        @NotBlank(message = "required field")
        @Size(min = 2, max = 150, message = "The field is outside the 2 to 150 character range.")
        @Schema(name = "nome", example = "Felipe Barbosa")
        String name,
        @NotBlank(message = "required field")
        @Size(min=11, max = 11, message = "The CPF field must contain 11 characters.")
        @ValidCpf
        @Schema(name = "cpf", example = "12345678910")
        String cpf,
        @NotBlank(message = "required field")
        @Size(max = 20, message = "Field exceeding 20 characters")
        @ValidPhone
        @Schema(name = "telefone", example = "88988888888")
        String phone,
        @Email(message = "Invalid email")
        @Size(min = 1, max = 254, message = "The field is outside the 1 to 254 character range.")
        @Schema(name = "email", example = "usuario@gmail.com")
        String email
) {
}
