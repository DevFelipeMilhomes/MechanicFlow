package com.devfelipemilhomes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "MechanicFlow API",
                version = "1.0.0",
                description = """
            API REST para gerenciamento de oficinas mecânicas.
            Permite o gerenciamento de clientes, veículos,
            ordens de serviço, profissionais e estoque.
            """
        )
)
@SecurityScheme(
        name = "mechanicflowOAuth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "${app.openapi.authorization-url}",
                        tokenUrl = "${app.openapi.token-url}",
                        scopes = {
                                @OAuthScope(
                                        name = "mechanicflow.api",
                                        description = "Acesso à API do MechanicFlow"
                                )
                        }
                )
        )
)
public class OpenApiConfig {
}
