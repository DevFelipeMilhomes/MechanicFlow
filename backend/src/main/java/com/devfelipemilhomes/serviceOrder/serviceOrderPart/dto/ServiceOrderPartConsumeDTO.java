package com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "Consumo de peça")
public record ServiceOrderPartConsumeDTO(
        @NotNull(message = "Required field")
        @Schema(name = "id_da_peça_ordem", example = "3")
        Long ServiceOrderPartId,
        @NotNull(message = "Required field")
        @Positive(message = "The quantity must be greater than zero.")
        @Schema(name = "quantidade_consumida", example = "2")
        Integer quantityConsume
) {
}
