package com.devfelipemilhomes.part;

import com.devfelipemilhomes.part.dto.PartRequestDTO;
import com.devfelipemilhomes.part.dto.PartResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("parts")
public class PartController {
    private final PartSevice sevice;

    public PartController(PartSevice sevice){
        this.sevice = sevice;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid PartRequestDTO dto){
        PartResponseDTO response = sevice.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PartResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(sevice.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid PartRequestDTO dto){
        sevice.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        sevice.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PartResponseDTO>> searchByExample(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "unitPrice", required = false)BigDecimal unitPrice
            ){
        return ResponseEntity.ok(sevice.searchByExample(name, description, unitPrice));
    }
}
