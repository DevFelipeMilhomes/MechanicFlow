package com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "Peça da ordem de serviço")
public record ServiceOrderPartSummaryDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "peça_id", example = "2")
        Long partId,
        @Schema(name = "nome_da_peça", example = "Óleo 20W50")
        String partName,
        @Schema(name = "quantidade_reservada", example = "2")
        Integer quantityReserved,
        @Schema(name = "quantidade_usada", example = "2")
        Integer quantityUsed,
        @Schema(name = "preço_unitário", example = "43.50")
        BigDecimal unitPrice
) {
}
