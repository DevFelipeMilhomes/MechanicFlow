package com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "Serviço resumido")
public record ServiceOrderServiceItemSummaryDTO(
        @Schema(name = "serviço_id", example = "3")
        Long serviceItemId,
        @Schema(name = "nome_do_serviço", example = "Troca de óleo")
        String serviceItemName,
        @Schema(name = "preço_unitário", example = "20.00")
        BigDecimal unitprice
) {
}
