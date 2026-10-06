package com.devfelipemilhomes.serviceOrder.dto;

import com.devfelipemilhomes.client.dto.ClientSummaryDTO;
import com.devfelipemilhomes.professional.dto.ProfessionalSummaryDTO;
import com.devfelipemilhomes.serviceOrder.ServiceOrderStatus;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartSummaryDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto.ServiceOrderServiceItemSummaryDTO;
import com.devfelipemilhomes.vehicle.dto.VehicleSummaryDTO;

import java.time.OffsetDateTime;
import java.util.Set;

public record ServiceOrderResponseDTO(
        Long id,
        VehicleSummaryDTO vehicleSummaryDTO,
        ClientSummaryDTO clientSummaryDTO,
        ProfessionalSummaryDTO professionalSummaryDTO,
        Set<ServiceOrderPartSummaryDTO> serviceOrderParts,
        Set<ServiceOrderServiceItemSummaryDTO> serviceOrderServiceItems,
        ServiceOrderStatus status,
        String reportedProblem,
        String diagnosis,
        Integer odometer,
        OffsetDateTime createdAt,
        OffsetDateTime completedAt,
        OffsetDateTime cancelledAt,
        String cancellationReason
) {
}
