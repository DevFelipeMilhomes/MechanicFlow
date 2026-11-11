package com.devfelipemilhomes.serviceOrder.dto;

import com.devfelipemilhomes.serviceOrder.ServiceOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Ordem de serviço para atualizar")
public record ServiceOrderUpdateDTO(
        @NotNull(message = "Required field")
        @Schema(name = "status", example = "OPEN")
        ServiceOrderStatus status,
        @Schema(name = "motivo_do_cancelamento", example = "falta de produto")
        String cancellationReason
) {
}
