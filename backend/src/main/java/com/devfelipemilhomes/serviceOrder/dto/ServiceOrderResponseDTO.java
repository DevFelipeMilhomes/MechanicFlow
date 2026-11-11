package com.devfelipemilhomes.serviceOrder.dto;

import com.devfelipemilhomes.client.dto.ClientSummaryDTO;
import com.devfelipemilhomes.professional.dto.ProfessionalSummaryDTO;
import com.devfelipemilhomes.serviceOrder.ServiceOrderStatus;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartSummaryDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto.ServiceOrderServiceItemSummaryDTO;
import com.devfelipemilhomes.vehicle.dto.VehicleSummaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Set;

@Schema(name = "Ordem de serviço retornada")
public record ServiceOrderResponseDTO(
        @Schema(name = "id", example = "3")
        Long id,
        @Schema(name = "veículo_resumido", example = "{id: 2, plate: AAA-0000, model: 'Mobi 1.0 2025'}")
        VehicleSummaryDTO vehicleSummaryDTO,
        @Schema(name = "cliente_resumido", example = "{id: 2, name: 'Felipe Barbosa', cpf: '12345678910', phone: '88988888888'}")
        ClientSummaryDTO clientSummaryDTO,
        @Schema(name = "cliente_resumido", example = "{id: 2, name: 'Felipe Barbosa', cpf: '12345678910', phone: '88988888888'}")
        ProfessionalSummaryDTO professionalSummaryDTO,
        @Schema(name = "lista_de_peças_resumidas", example = "[{id: 2, partId: 3, partName: 'Óleo 20W50', " +
                "quantityReserved: 3, quantityUsed: 2, unitPrice: 43.50}]")
        Set<ServiceOrderPartSummaryDTO> serviceOrderParts,
        @Schema(name = "lista_de_serviços_resumidos", example = "[{serviceItemId: 4, serviceItemName: 'Troca de óleo', " +
                "unitPrice: 20.00}]")
        Set<ServiceOrderServiceItemSummaryDTO> serviceOrderServiceItems,
        @Schema(name = "status", example = "OPEN")
        ServiceOrderStatus status,
        @Schema(name = "problema_relatado", example = "Óleo baixo e escuro")
        String reportedProblem,
        @Schema(name = "diagnóstico", example = "Trocar óleo")
        String diagnosis,
        @Schema(name = "odômetro", example = "124004")
        Integer odometer,
        @Schema(name = "criado_em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime createdAt,
        @Schema(name = "completado_em", example = "2026-09-22T21:42:24.178972Z")
        OffsetDateTime completedAt,
        @Schema(name = "cancelado_em", example = "2026-09-21T21:42:24.178972Z")
        OffsetDateTime cancelledAt,
        @Schema(name = "motivo_do_cancelamento", example = "falta de produto")
        String cancellationReason
) {
}
