package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.stock.dto.StockRequestDTO;
import com.devfelipemilhomes.stock.dto.StockResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("stocks")
public class StockController {
    private final StockService service;

    private StockController(StockService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid StockRequestDTO dto){
        StockResponseDTO response = service.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<StockResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid StockRequestDTO dto){
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
}
