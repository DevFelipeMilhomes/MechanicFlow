package com.devfelipemilhomes.serviceItem;

import com.devfelipemilhomes.serviceItem.dto.ServiceItemRequestDTO;
import com.devfelipemilhomes.serviceItem.dto.ServiceItemResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/serviceItems")
@Tag(
        name = "Serviços",
        description = "Gerenciamento de serviços da oficina"
)
@SecurityRequirement(
        name = "mechanicflowOAuth2",
        scopes = {"mechanicflow.api"}
)
public class ServiceItemController {
    private final ServiceItemService service;

    public ServiceItemController(ServiceItemService service){
        this.service = service;
    }

    @Operation(
            summary = "Adicionar um novo serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Serviço cadastrado"
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
                    description = "Serviço já cadastrado"
            )
    })
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

    @Operation(
            summary = "Buscar serviço por ID.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Serviço encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado"
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
    public ResponseEntity<ServiceItemResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualizar serviço por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Serviço atualizado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado"
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
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid ServiceItemRequestDTO dto){
        service.update(id,dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar serviço por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Serviço deletado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado"
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
            summary = "Buscar serviços com filtros.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Serviços encontrados"
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
    public ResponseEntity<List<ServiceItemResponseDTO>> searchByExample(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "basePrice", required = false) BigDecimal basePrice
            ){
        return ResponseEntity.ok(service.searchByExample(name, description, basePrice));
    }
}
