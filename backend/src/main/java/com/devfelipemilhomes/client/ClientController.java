package com.devfelipemilhomes.client;

import com.devfelipemilhomes.client.dto.ClientRequestDTO;
import com.devfelipemilhomes.client.dto.ClientResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {

    private ClientService service;

    public ClientController(ClientService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ClientRequestDTO dto){

        ClientResponseDTO response = service.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable("id") Long id){
        ClientResponseDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email
    )
    {List<ClientResponseDTO> response = service.searchByExample(name, cpf, phone, email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ClientResponseDTO> uptade(@PathVariable("id") Long id, @Valid @RequestBody ClientRequestDTO dto){

            ClientResponseDTO response = service.update(id, dto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.id())
                    .toUri();
            return ResponseEntity.noContent().location(location).build();

    }
}
