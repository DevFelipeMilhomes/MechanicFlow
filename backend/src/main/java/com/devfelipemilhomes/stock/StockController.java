package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.stock.dto.StockRequestDTO;
import com.devfelipemilhomes.stock.dto.StockResponseDTO;
import com.devfelipemilhomes.stock.stockMoviment.StockMovimentResponseDTO;
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
@RequestMapping("api/stocks")
@Tag(
        name = "Estoques",
        description = "Gerenciamento de estoques da oficina"
)
@SecurityRequirement(
        name = "mechanicflowOAuth2",
        scopes = {"mechanicflow.api"}
)
public class StockController {
    private final StockService service;

    private StockController(StockService service){
        this.service = service;
    }

    @Operation(
            summary = "Adicionar um novo estoque.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Estoque cadastrado"
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
                    responseCode = "404",
                    description = "Estoque ou peça não encontrado"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Estoque já cadastrado"
            )
    })
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

    @Operation(
            summary = "Buscar estoque por ID.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estoque encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estoque não encontrado"
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
    public ResponseEntity<StockResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualizar estoque por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Estoque atualizado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estoque ou peça não encontrado"
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
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid StockRequestDTO dto){
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar estoque por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Estoque deletado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estoque não encontrado"
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
            summary = "Buscar todos os estoques do sistema.",
            description = """
        Exige autenticação OAuth2 e
        escopo mechanicflow.api
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estoques encontrados"
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
    public ResponseEntity<List<StockResponseDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Buscar histórico de movimentos no estoque.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE ou MECANICO
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Histórico encontrado"
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
    @GetMapping("stock-moviment")
    public ResponseEntity<List<StockMovimentResponseDTO>> findAllStockMoviment(){
        return ResponseEntity.ok(service.findAllStockMoviment());
    }
}
