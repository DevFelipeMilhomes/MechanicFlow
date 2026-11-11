package com.devfelipemilhomes.part;

import com.devfelipemilhomes.part.dto.PartRequestDTO;
import com.devfelipemilhomes.part.dto.PartResponseDTO;
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
@RequestMapping("api/parts")
@Tag(
        name = "Peças",
        description = "Gerenciamento de peças da oficina"
)
@SecurityRequirement(
        name = "mechanicflowOAuth2",
        scopes = {"mechanicflow.api"}
)
public class PartController {
    private final PartSevice sevice;

    public PartController(PartSevice sevice){
        this.sevice = sevice;
    }

    @Operation(
            summary = "Adicionar uma nova peça.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Peça cadastrada"
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
                    description = "Peça já cadastrada"
            )
    })
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

    @Operation(
            summary = "Buscar peça por ID.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Peça encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Peça não encontrada"
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
    public ResponseEntity<PartResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(sevice.findById(id));
    }

    @Operation(
            summary = "Atualizar peça por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Peça atualizada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Peça não encontrada"
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
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid PartRequestDTO dto){
        sevice.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar peça por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Peça deletada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Peça não encontrada"
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
        sevice.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Buscar peças com filtros.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Peças encontradas"
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
    public ResponseEntity<List<PartResponseDTO>> searchByExample(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "unitPrice", required = false)BigDecimal unitPrice
            ){
        return ResponseEntity.ok(sevice.searchByExample(name, description, unitPrice));
    }
}
