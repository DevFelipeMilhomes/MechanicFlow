package com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "Devolução de peça")
public record ServiceOrderPartReleaseDTO(
        @NotNull(message = "Required field")
        @Schema(name = "id_da_peça_ordem", example = "3")
        Long ServiceOrderPartId,
        @Positive(message = "The quantity must be greater than zero.")
        @Schema(name = "quantidade_devolvida", example = "2")
        Integer quantityRelease
) {
}
