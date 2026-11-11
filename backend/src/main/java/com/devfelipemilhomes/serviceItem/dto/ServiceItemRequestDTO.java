package com.devfelipemilhomes.serviceItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(name = "Serviço requisitado")
public record ServiceItemRequestDTO(
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 150, message = "The field is outside the 2 to 150 character range.")
        @Schema(name = "nome", example = "Troca de filtros")
        String name,
        @Schema(name = "descrição", example = "Troca de filtros de ar, combustivel ou óleo")
        String description,
        @NotNull
        @PositiveOrZero(message = "The price must be greater than or equal to zero.")
        @Digits(integer = 8, fraction = 2,
                message = "The price must have a maximum of 8 integer digits and 2 decimal places.")
        @Schema(name = "preço_base", example = "20.00")
        BigDecimal basePrice
) {
}
