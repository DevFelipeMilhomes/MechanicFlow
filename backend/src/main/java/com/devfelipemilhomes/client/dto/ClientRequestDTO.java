package com.devfelipemilhomes.client.dto;

import com.devfelipemilhomes.validation.ValidCpf;
import com.devfelipemilhomes.validation.ValidPhone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequestDTO(
        @NotBlank(message = "required field")
        @Size(min = 2, max = 150, message = "The field is outside the 2 to 150 character range.")
        String name,
        @NotBlank(message = "required field")
        @Size(min=11, max = 11, message = "The CPF field must contain 11 characters.")
        @ValidCpf
        String cpf,
        @NotBlank(message = "required field")
        @Size(max = 20, message = "Field exceeding 20 characters")
        @ValidPhone
        String phone,
        @Email(message = "Invalid email")
        @Size(min = 1, max = 254, message = "The field is outside the 1 to 254 character range.")
        String email
) {
}
