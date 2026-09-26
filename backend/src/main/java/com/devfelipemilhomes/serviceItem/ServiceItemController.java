package com.devfelipemilhomes.serviceItem;

import com.devfelipemilhomes.serviceItem.dto.ServiceItemRequestDTO;
import com.devfelipemilhomes.serviceItem.dto.ServiceItemResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("serviceItems")
public class ServiceItemController {
    private final ServiceItemService service;

    public ServiceItemController(ServiceItemService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ServiceItemRequestDTO dto){
        ServiceItemResponseDTO response = service.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ServiceItemResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid ServiceItemRequestDTO dto){
        service.update(id,dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ServiceItemResponseDTO>> searchByExample(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "basePrice", required = false) BigDecimal basePrice
            ){
        return ResponseEntity.ok(service.searchByExample(name, description, basePrice));
    }
}
