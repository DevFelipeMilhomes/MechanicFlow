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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/service-orders")
@Tag(
        name = "Ordens de serviço",
        description = "Gerenciamento de ordens de serviço da oficina"
)
@SecurityRequirement(
        name = "mechanicflowOAuth2",
        scopes = {"mechanicflow.api"}
)
public class ServiceOrderController {
    private final ServiceOrderService service;

    public ServiceOrderController(ServiceOrderService service){
        this.service = service;
    }

    // ------------- SERVICE ORDERS ENDPOINTS -------------
    @Operation(
            summary = "Adicionar uma nova orden de serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Ordem de serviço criada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente, profissional ou veículo não encontrado"
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

    @Operation(
            summary = "Buscar ordem de serviço por ID.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ordem de serviço encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
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
    public ResponseEntity<ServiceOrderResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualizar ordem de serviço por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Ordem de serviço atualizada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
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
                    description = "Mudança de status não permitida"
            )
    })
    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderUpdateDTO dto){
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar ordem de serviço por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Ordem de serviço deletada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
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


    // ------------- ENDPOINTS FOR PARTS OF SERVCE ORDERS -------------
    @Operation(
            summary = "Adicionar peça à ordem de serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Peça adicionada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço ou peça não encontrada"
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
                    description = "A peça já foi adicionada"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Quantidade não permida"
            )
    })
    @PostMapping("{id}/parts")
    public ResponseEntity<Void> addPart(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderPartReserveDTO dto){
        service.addPart(id, dto);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "Reservar mais peças para a ordem de serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Peças reservadas"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Peça não adicionada á ordem de serviço"
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
                    description = "Quantidade não permida"
            )
    })
    @PatchMapping("{id}/parts/reserveMore")
    public ResponseEntity<Void> reserveMoreParts(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderPartReserveMoreDTO dto){
        service.reserveMoreParts(id, dto);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "Consumir peças de uma ordem de serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Peças consumidas"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Peça não adicionada á ordem de serviço"
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
                    description = "Quantidade não permida"
            )
    })
    @PatchMapping("{id}/parts/consume")
    public ResponseEntity<Void> consumePart(
            @PathVariable("id") Long id,
            @RequestBody @Valid ServiceOrderPartConsumeDTO dto
            ){
        service.consumePart(id, dto);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "Devolver peças não utilizadas de uma ordem de serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Peças devolvidas"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Peça não adicionada á ordem de serviço"
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
                    description = "Quantidade não permida"
            )
    })
    @PatchMapping("{id}/parts/release")
    public ResponseEntity<Void> releaseToStock(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderPartReleaseDTO dto){
        service.releaseToStock(id, dto);
        return ResponseEntity.accepted().build();
    }


    // ------------- ENDPOINTS FOR SERVICE ITEMS OF SERVCE ORDERS -------------

    @Operation(
            summary = "Adicionar serviço a uma ordem de serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Serviço adicionado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
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
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Serviço já adicionado a ordem de serviço"
            )
    })
    @PostMapping("{id}/service-items")
    public ResponseEntity<Void> addServiceItem(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderServiceItemAddDTO dto){
        service.addServiceItem(id, dto);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "Remover serviço de uma ordem de serviço.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel MECANICO.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Serviço removido"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ordem de serviço não encontrada"
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
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Serviço não pertence a ordem de serviço"
            )
    })
    @PatchMapping("{id}/service-items")
    public ResponseEntity<Void> removeServiceItem(@PathVariable("id") Long id, @RequestBody @Valid ServiceOrderServiceItemRemoveDTO dto){
        service.removeServiceItem(id, dto);
        return ResponseEntity.accepted().build();
    }

}
