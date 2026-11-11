package com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Adição de serviço")
public record ServiceOrderServiceItemAddDTO(
        @NotNull(message = "Required field")
        @Schema(name = "serviço_id", example = "3")
        Long serviceItemId
) {
}
