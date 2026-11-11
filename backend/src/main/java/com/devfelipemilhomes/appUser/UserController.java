package com.devfelipemilhomes.appUser;

import com.devfelipemilhomes.appUser.dto.UserRequestDTO;
import com.devfelipemilhomes.appUser.dto.UserResponseDTO;
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
@RequestMapping("api/users")
@Tag(
        name = "Usuários",
        description = "Gerenciamento de usuário da oficina"
)
@SecurityRequirement(
        name = "mechanicflowOAuth2",
        scopes = {"mechanicflow.api"}
)
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @Operation(
            summary = "Adicionar um novo usuário.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado"
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
                    description = "Profissional não encontrado"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário já cadastrado"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserRequestDTO dto){
        UserResponseDTO response = service.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Buscar usuário por ID.",
            description = """
        Exige autenticação OAuth2,
        escopo mechanicflow.api
        e papel GERENTE
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
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
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
}
