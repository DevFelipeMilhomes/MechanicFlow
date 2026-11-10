package com.devfelipemilhomes.stock.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(name = "Estoque requisitado")
public record StockRequestDTO(
        @NotNull(message = "Required field")
        @Schema(name = "peça_id", example = "2")
        Long partId,
        @NotNull(message = "Required field")
        @PositiveOrZero(message = "The quantity must be greater than or equal to zero.")
        @Digits(integer = 8, fraction = 0,
                message = "The quantity must be an integer with a maximum of 8 digits..")
        @Schema(name = "quantidade_em_mãos", example = "10")
        int quantityOnHand,
        @NotNull(message = "Required field")
        @PositiveOrZero(message = "The quantity must be greater than or equal to zero.")
        @Digits(integer = 8, fraction = 0,
                message = "The quantity must be an integer with a maximum of 8 digits..")
        @Schema(name = "quantidade_reservada", example = "3")
        int quantityReserved
) {
}
