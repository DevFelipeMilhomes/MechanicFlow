package com.devfelipemilhomes.serviceOrder;

import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderRequestDTO;
import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderResponseDTO;
import com.devfelipemilhomes.serviceOrder.dto.ServiceOrderUpdateDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartConsumeDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartReleaseDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartReserveDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartReserveMoreDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto.ServiceOrderServiceItemAddDTO;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto.ServiceOrderServiceItemRemoveDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("service-orders")
public class ServiceOrderController {
    private final ServiceOrderService service;

    public ServiceOrderController(ServiceOrderService service){
        this.service = service;
    }

    // ------------- SERVICE ORDERS ENDPOINTS -------------
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ServiceOrderRequestDTO dto){
        ServiceOrderResponseDTO response = service.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ServiceOrderResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderUpdateDTO dto){
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    // ------------- ENDPOINTS FOR PARTS OF SERVCE ORDERS -------------
    @PostMapping("{id}/parts")
    public ResponseEntity<Void> addPart(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderPartReserveDTO dto){
        service.addPart(id, dto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("{id}/parts/reserveMore")
    public ResponseEntity<Void> reserveMoreParts(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderPartReserveMoreDTO dto){
        service.reserveMoreParts(id, dto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("{id}/parts/consume")
    public ResponseEntity<Void> consumePart(
            @PathVariable("id") Long id,
            @RequestBody @Valid ServiceOrderPartConsumeDTO dto
            ){
        service.consumePart(id, dto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("{id}/parts/release")
    public ResponseEntity<Void> releaseToStock(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderPartReleaseDTO dto){
        service.releaseToStock(id, dto);
        return ResponseEntity.accepted().build();
    }


    // ------------- ENDPOINTS FOR SERVICE ITEMS OF SERVCE ORDERS -------------

    @PostMapping("{id}/service-items")
    public ResponseEntity<Void> addServiceItem(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderServiceItemAddDTO dto){
        service.addServiceItem(id, dto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("{id}/service-items")
    public ResponseEntity<Void> removeServiceItem(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderServiceItemRemoveDTO dto){
        service.removeServiceItem(id, dto);
        return ResponseEntity.accepted().build();
    }

}
