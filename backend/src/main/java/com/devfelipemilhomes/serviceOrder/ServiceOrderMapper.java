package com.devfelipemilhomes.serviceOrder;

import com.devfelipemilhomes.client.Client;
import com.devfelipemilhomes.client.dto.ClientSummaryDTO;
import com.devfelipemilhomes.professional.Professional;
import com.devfelipemilhomes.professional.dto.ProfessionalSummaryDTO;
import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderRequestDTO;
import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderResponseDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartSummaryDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto.ServiceOrderServiceItemSummaryDTO;
import com.devfelipemilhomes.vehicle.Vehicle;
import com.devfelipemilhomes.vehicle.dto.VehicleSummaryDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ServiceOrderMapper {
    public ServiceOrder toEntity(ServiceOrderRequestDTO dto, Client client, Professional professional, Vehicle vehicle){
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setStatus(ServiceOrderStatus.OPEN);
        serviceOrder.setClient(client);
        serviceOrder.setProfessional(professional);
        serviceOrder.setVehicle(vehicle);
        serviceOrder.setReportedProblem(dto.reportedProblem());
        serviceOrder.setDiagnosis(dto.diagnosis());
        serviceOrder.setOdometer(dto.odometer());
        return serviceOrder;
    }

    public ServiceOrderResponseDTO toResponse(ServiceOrder serviceOrder){
        VehicleSummaryDTO vehicleSummaryDTO = new VehicleSummaryDTO(
                serviceOrder.getVehicle().getId(),
                serviceOrder.getVehicle().getPlate(),
                serviceOrder.getVehicle().getModel()
        );
        ClientSummaryDTO clientSummaryDTO = new ClientSummaryDTO(
                serviceOrder.getClient().getId(),
                serviceOrder.getClient().getName(),
                serviceOrder.getClient().getCpf(),
                serviceOrder.getClient().getPhone()
        );
        ProfessionalSummaryDTO professionalSummaryDTO = new ProfessionalSummaryDTO(
                serviceOrder.getProfessional().getId(),
                serviceOrder.getProfessional().getName(),
                serviceOrder.getProfessional().getCpf(),
                serviceOrder.getProfessional().getPhone()
        );

        Set<ServiceOrderPartSummaryDTO> serviceOrderPartSummaryDTOS = serviceOrder.getServiceOrderParts()
                .stream().map(
                        sp -> new ServiceOrderPartSummaryDTO(
                                sp.getId(),
                                sp.getPart().getId(),
                                sp.getPart().getName(),
                                sp.getQuantityReserved(),
                                sp.getQuantityUsed(),
                                sp.getUnitPrice()
                        )
                ).collect(Collectors.toSet());

        Set<ServiceOrderServiceItemSummaryDTO> serviceOrderServiceItemSummaryDTOS = serviceOrder.getServiceOrderServiceItems()
                .stream().map(
                        si -> new ServiceOrderServiceItemSummaryDTO(
                                si.getServiceItem().getId(),
                                si.getServiceItem().getName(),
                                si.getUnitPrice()
                        )
                ).collect(Collectors.toSet());

        return new ServiceOrderResponseDTO(
                serviceOrder.getId(),
                vehicleSummaryDTO,
                clientSummaryDTO,
                professionalSummaryDTO,
                serviceOrderPartSummaryDTOS,
                serviceOrderServiceItemSummaryDTOS,
                serviceOrder.getStatus(),
                serviceOrder.getReportedProblem(),
                serviceOrder.getDiagnosis(),
                serviceOrder.getOdometer(),
                serviceOrder.getCreatedAt(),
                serviceOrder.getCompletedAt(),
                serviceOrder.getCancelledAt(),
                serviceOrder.getCancellationReason()
        );
    }
}
