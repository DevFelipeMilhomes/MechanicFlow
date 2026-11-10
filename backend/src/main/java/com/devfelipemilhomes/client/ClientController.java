package com.devfelipemilhomes.client;

import com.devfelipemilhomes.client.dto.ClientRequestDTO;
import com.devfelipemilhomes.client.dto.ClientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/clients")
@Tag(
        name = "Clientes",
        description = "Gerenciamento de clientes da oficina"
)
@SecurityRequirement(
        name = "mechanicflowOAuth2",
        scopes = {"mechanicflow.api"}
)
public class ClientController {

    private ClientService service;

    public ClientController(ClientService service){
        this.service = service;
    }

    @Operation(
            summary = "Adicionar um novo cliente.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente cadastrado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Cliente já cadastrado"
            )
    })
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

    @Operation(
            summary = "Buscar cliente por ID.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado"
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable("id") Long id){
        ClientResponseDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deletar cliente por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cliente deletado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado"
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Buscar clientes com filtros.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Clientes encontrados"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado"
            )
    })
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email
    )
    {
        List<ClientResponseDTO> response = service.searchByExample(name, cpf, phone, email);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualizar cliente por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cliente atualizado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado"
            )
    })
    @PutMapping("{id}")
    public ResponseEntity<Void> uptade(@PathVariable("id") Long id, @Valid @RequestBody ClientRequestDTO dto){
            service.update(id, dto);
            return ResponseEntity.noContent().build();
    }
}
