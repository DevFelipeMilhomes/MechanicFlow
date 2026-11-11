package com.devfelipemilhomes.role;

import com.devfelipemilhomes.role.dto.RoleAssignRequestDTO;
import com.devfelipemilhomes.role.dto.RoleRequestDTO;
import com.devfelipemilhomes.role.dto.RoleResponseDTO;
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
import java.util.List;

@RestController
@RequestMapping("api/roles")
@Tag(
        name = "Funções",
        description = "Gerenciamento de funções da oficina"
)
@SecurityRequirement(
        name = "mechanicflowOAuth2",
        scopes = {"mechanicflow.api"}
)
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service){
        this.service = service;
    }

    @Operation(
            summary = "Adicionar uma nova função.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Função cadastrada"
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
                    description = "Função já cadastrada"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid RoleRequestDTO dto){
        RoleResponseDTO response = service.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Buscar função por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Função encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Função não encontrada"
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
    public ResponseEntity<RoleResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Buscar todas as funções existentes.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Funções encontradas"
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
    public ResponseEntity<List<RoleResponseDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Atualizar função por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Função atualizada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Função não encontrada"
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
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid RoleRequestDTO dto){
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar função por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Função deletada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Função não encontrada"
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
}
