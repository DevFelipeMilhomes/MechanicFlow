package com.devfelipemilhomes.serviceOrder;

import com.devfelipemilhomes.client.ClientRepository;
import com.devfelipemilhomes.exception.DuplicateFieldException;
import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.part.PartRepository;
import com.devfelipemilhomes.professional.ProfessionalRepository;
import com.devfelipemilhomes.serviceItem.ServiceItem;
import com.devfelipemilhomes.serviceItem.ServiceItemRepository;
import com.devfelipemilhomes.serviceOrder.dto.ReservationNotYetReturned;
import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderRequestDTO;
import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderResponseDTO;
import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderUpdateDTO;
import com.devfelipemilhomes.serviceOrder.exception.StatusReturnNotAllowed;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.*;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartConsumeDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartReleaseDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartReserveDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartReserveMoreDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.ServiceOrderServiceItem;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.ServiceOrderServiceItemRepository;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto.ServiceOrderServiceItemAddDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto.ServiceOrderServiceItemRemoveDTO;
import com.devfelipemilhomes.stock.StockService;
import com.devfelipemilhomes.stock.exception.ConsumptionExceedingReserves;
import com.devfelipemilhomes.stock.exception.QuantityReleaseExceedingUnconsumed;
import com.devfelipemilhomes.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Set;

@Service
public class ServiceOrderService {
    private final ServiceOrderMapper mapper;
    private final ServiceOrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProfessionalRepository professionalRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceOrderPartRepository serviceOrderPartRepository;
    private final ServiceOrderServiceItemRepository serviceOrderServiceItemRepository;
    private final PartRepository partRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final StockService stockService;

    public ServiceOrderService(
            ServiceOrderMapper mapper,
            ServiceOrderRepository repository,
            ClientRepository clientRepository,
            ProfessionalRepository professionalRepository,
            VehicleRepository vehicleRepository,
            ServiceOrderPartRepository serviceOrderPartRepository,
            ServiceOrderServiceItemRepository serviceOrderServiceItemRepository,
            PartRepository partRepository,
            ServiceItemRepository serviceItemRepository,
            StockService stockService
    ){
        this.mapper = mapper;
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.professionalRepository = professionalRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceOrderPartRepository = serviceOrderPartRepository;
        this.serviceOrderServiceItemRepository = serviceOrderServiceItemRepository;
        this.partRepository = partRepository;
        this.stockService = stockService;
        this.serviceItemRepository = serviceItemRepository;
    }

    public ServiceOrderResponseDTO create(ServiceOrderRequestDTO dto){
        ServiceOrder serviceOrder = mapper.toEntity(
                dto,
                clientRepository.findById(dto.clientId()).orElseThrow(()->new ResourceNotFoundException("Client not found")),
                professionalRepository.findById(dto.professionalId()).orElseThrow(()-> new ResourceNotFoundException("Professional not found")),
                vehicleRepository.findById(dto.vehicleId()).orElseThrow(()->new ResourceNotFoundException("Vehicle not found"))
        );
        repository.save(serviceOrder);
        return mapper.toResponse(serviceOrder);
    }

    public ServiceOrderResponseDTO findById(Long id){
        return mapper.toResponse(repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service order not found")));
    }

    public void update(Long id, ServiceOrderUpdateDTO dto){
        ServiceOrder serviceOrder = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service order not found"));
        ServiceOrderStatus status = serviceOrder.getStatus();

        validateModifyStatus(dto.status(), status);

        if((dto.status()==ServiceOrderStatus.CANCELLED)&&(status!=ServiceOrderStatus.CANCELLED)){
            serviceOrder.setCancelledAt(OffsetDateTime.now());
            serviceOrder.setCancellationReason(dto.cancellationReason());
            verifyReleaseToStock(serviceOrder.getServiceOrderParts());
            serviceOrder.setStatus(dto.status());
            repository.save(serviceOrder);
        }

        if((dto.status()==ServiceOrderStatus.COMPLETED)&&(status!=ServiceOrderStatus.COMPLETED)){
            serviceOrder.setCompletedAt(OffsetDateTime.now());
            serviceOrder.setCancellationReason(dto.cancellationReason());
            verifyReleaseToStock(serviceOrder.getServiceOrderParts());
            serviceOrder.setStatus(dto.status());
            repository.save(serviceOrder);
        }
    }

    public void delete(Long id){
        ServiceOrder serviceOrder = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service order not found"));
        repository.delete(serviceOrder);
    }

    public void addPart(Long id, ServiceOrderPartReserveDTO dto){
        ServiceOrder serviceOrder = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service order not found"));
        Part part = partRepository.findById(dto.partId()).orElseThrow(()->new ResourceNotFoundException("Part not found"));
        if(serviceOrderPartRepository.existsByServiceOrderAndPart(serviceOrder, part)){
            throw new DuplicateFieldException("Part already added to the service order.");
        }
        ServiceOrderPart serviceOrderPart = new ServiceOrderPart();
        serviceOrderPart.setPart(part);
        serviceOrderPart.setServiceOrder(serviceOrder);
        serviceOrderPart.setQuantityReserved(dto.quantityReserved());
        serviceOrderPart.setQuantityUsed(0);
        serviceOrderPart.setUnitPrice(part.getUnitPrice());
        stockService.reserve(dto.quantityReserved(), dto.partId());
        serviceOrderPartRepository.save(serviceOrderPart);
    }

    public void reserveMoreParts(Long id, ServiceOrderPartReserveMoreDTO dto){
        ServiceOrderPart serviceOrderPart = serviceOrderPartRepository.findById(dto.ServiceOrderPartId())
                .orElseThrow(()->new ResourceNotFoundException("Part related to the service order not found"));
        if (!serviceOrderPart.getServiceOrder().getId().equals(id)) {
            throw new ResourceNotFoundException(
                    "Part does not belong to this service order"
            );
        }
        stockService.reserve(dto.quantityReserved(), serviceOrderPart.getPart().getId());
        serviceOrderPart.setQuantityReserved(serviceOrderPart.getQuantityReserved()+dto.quantityReserved());
        serviceOrderPartRepository.save(serviceOrderPart);
    }

    public void consumePart(Long id, ServiceOrderPartConsumeDTO dto){
        ServiceOrderPart serviceOrderPart = serviceOrderPartRepository.findById(dto.ServiceOrderPartId())
                .orElseThrow(()->new ResourceNotFoundException("Part related to the service order not found"));
        if (!serviceOrderPart.getServiceOrder().getId().equals(id)) {
            throw new ResourceNotFoundException(
                    "Part does not belong to this service order"
            );
        }
        Integer unconsumedQuantity = serviceOrderPart.getQuantityReserved() - serviceOrderPart.getQuantityUsed();
        if(dto.quantityConsume()>unconsumedQuantity){
            throw new ConsumptionExceedingReserves("The amount used cannot exceed the reserved amount.");
        }
        stockService.consume(dto.quantityConsume(), serviceOrderPart.getPart().getId());
        serviceOrderPart.setQuantityUsed(serviceOrderPart.getQuantityUsed()+dto.quantityConsume());
        serviceOrderPartRepository.save(serviceOrderPart);
    }

    public void releaseToStock(Long id, ServiceOrderPartReleaseDTO dto){
        ServiceOrderPart serviceOrderPart = serviceOrderPartRepository.findById(dto.ServiceOrderPartId())
                .orElseThrow(()->new ResourceNotFoundException("Part related to the service order not found"));
        if (!serviceOrderPart.getServiceOrder().getId().equals(id)) {
            throw new ResourceNotFoundException(
                    "Part does not belong to this service order"
            );
        }
        Integer unconsumedQuantity = serviceOrderPart.getQuantityReserved() - serviceOrderPart.getQuantityUsed();
        if(unconsumedQuantity>0){

            boolean releaseAll = dto.quantityRelease() == null
                    || dto.quantityRelease().compareTo(unconsumedQuantity) == 0;
            boolean nothingUsed = serviceOrderPart.getQuantityUsed().equals(0);

            if(releaseAll && nothingUsed){
                stockService.release(dto.quantityRelease(), serviceOrderPart.getPart().getId());
                serviceOrderPartRepository.delete(serviceOrderPart);
                return;

            } else if(releaseAll){
                stockService.release(unconsumedQuantity, serviceOrderPart.getPart().getId());
                serviceOrderPart.setQuantityReserved(serviceOrderPart.getQuantityUsed());

            } else if (dto.quantityRelease().compareTo(unconsumedQuantity)<0){
                stockService.release(dto.quantityRelease(), serviceOrderPart.getPart().getId());
                serviceOrderPart.setQuantityReserved(serviceOrderPart.getQuantityReserved()-dto.quantityRelease());

            } else {
                throw new QuantityReleaseExceedingUnconsumed("The quantity to be released exceeds the unconsumed quantity.");
            }
            serviceOrderPartRepository.save(serviceOrderPart);
        }

    }

    public void addServiceItem(Long id, ServiceOrderServiceItemAddDTO dto){
        ServiceOrder serviceOrder = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Service order not found"));
        ServiceItem serviceItem = serviceItemRepository.findById(dto.serviceItemId())
                .orElseThrow(()-> new ResourceNotFoundException("Service item not found"));
        if(serviceOrderServiceItemRepository.existsByServiceOrderAndServiceItem(serviceOrder,serviceItem)){
            throw new DuplicateFieldException("Service item already added to the service order.");
        }
        ServiceOrderServiceItem serviceOrderServiceItem = new ServiceOrderServiceItem();
        serviceOrderServiceItem.setServiceOrder(serviceOrder);
        serviceOrderServiceItem.setServiceItem(serviceItem);
        serviceOrderServiceItem.setUnitPrice(serviceItem.getBasePrice());
        serviceOrderServiceItemRepository.save(serviceOrderServiceItem);
    }

    public void removeServiceItem(Long id, ServiceOrderServiceItemRemoveDTO dto){
        ServiceOrder serviceOrder = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Service order not found"));
        ServiceItem serviceItem = serviceItemRepository.findById(dto.serviceItemId())
                .orElseThrow(()-> new ResourceNotFoundException("Service item not found"));
        ServiceOrderServiceItem serviceOrderServiceItem = serviceOrderServiceItemRepository
                .findByServiceOrderAndServiceItem(serviceOrder, serviceItem)
                .orElseThrow(()->new ReservationNotYetReturned("Service item does not belong to this service order"));

        serviceOrderServiceItemRepository.delete(serviceOrderServiceItem);
    }

    public static void validateModifyStatus(ServiceOrderStatus requestStatus, ServiceOrderStatus currentStatus){
        if((requestStatus==ServiceOrderStatus.IN_PROGRESS||requestStatus==ServiceOrderStatus.OPEN)
                &&(currentStatus==ServiceOrderStatus.CANCELLED||currentStatus==ServiceOrderStatus.COMPLETED)){
            throw new StatusReturnNotAllowed("It is not permitted to modify a completed or cancelled order.");
        }

        if((requestStatus==ServiceOrderStatus.CANCELLED)&&(currentStatus==ServiceOrderStatus.COMPLETED)){
            throw new StatusReturnNotAllowed("It is not permitted to modify a completed or cancelled order.");
        }

        if((requestStatus==ServiceOrderStatus.COMPLETED)&&(currentStatus==ServiceOrderStatus.CANCELLED)){
            throw new StatusReturnNotAllowed("It is not permitted to modify a completed or cancelled order.");
        }
    }

    private static void verifyReleaseToStock(Set<ServiceOrderPart> serviceOrderParts){
        for (ServiceOrderPart sp : serviceOrderParts) {
            int quantityToRelease =
                    sp.getQuantityReserved() - sp.getQuantityUsed();

            if (quantityToRelease > 0) {
                throw new ReservationNotYetReturned("It is not permitted to cancel or complete an order with unused part reservations.");
            }
        }
    }
}
