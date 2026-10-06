package com.devfelipemilhomes.part.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PartRequestDTO(
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 150, message = "The field is outside the 2 to 150 character range.")
        String name,
        String description,
        @NotNull(message = "Required field")
        @PositiveOrZero(message = "The price must be greater than or equal to zero.")
        @Digits(integer = 8, fraction = 2,
                message = "The price must have a maximum of 8 integer digits and 2 decimal places.")
        BigDecimal unitPrice
) {
}
