package com.devfelipemilhomes.professional;

import com.devfelipemilhomes.professional.dto.ProfessionalRequestDTO;
import com.devfelipemilhomes.professional.dto.ProfessionalResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("professionals")
public class ProfessionalController {
    private final ProfessionalService service;

    public ProfessionalController(ProfessionalService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProfessionalRequestDTO dto){
        ProfessionalResponseDTO response = service.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfessionalResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid ProfessionalRequestDTO dto){
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalResponseDTO>> searchByExample(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email
    ){
        List<ProfessionalResponseDTO> responseDTOList = service.searchByExample(name, cpf, phone, email);
        return ResponseEntity.ok(responseDTOList);
    }
}
