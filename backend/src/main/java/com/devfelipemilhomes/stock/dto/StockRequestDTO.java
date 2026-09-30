package com.devfelipemilhomes.stock.dto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockRequestDTO(
        @NotNull(message = "Required field")
        Long partId,
        @NotNull(message = "Required field")
        @PositiveOrZero(message = "The quantity must be greater than or equal to zero.")
        @Digits(integer = 8, fraction = 0,
                message = "The quantity must be an integer with a maximum of 8 digits..")
        int quantityOnHand,
        @NotNull(message = "Required field")
        @PositiveOrZero(message = "The quantity must be greater than or equal to zero.")
        @Digits(integer = 8, fraction = 0,
                message = "The quantity must be an integer with a maximum of 8 digits..")
        int quantityReserved
) {
}
