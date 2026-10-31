package com.devfelipemilhomes.security.authorizationServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class OAuth2ClientSeedConfig {

    @Bean
    ApplicationRunner registerPostmanClient(
            RegisteredClientRepository repository,
            PasswordEncoder encoder,
            @Value("${app.oauth2.postman-client-secret}")
            String clientSecret
    ){
        return args -> {
            String clientId = "mechanicflow-postman";
            if (repository.findByClientId(clientId) != null) {
                return;
            }

            RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(clientId)
                    .clientName("MechanicFlow Postman")
                    .clientSecret(encoder.encode(clientSecret))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri("https://oauth.pstmn.io/v1/callback")
                    .scope(OidcScopes.OPENID)
                    .scope("mechanicflow.api")
                    .clientSettings(
                            ClientSettings.builder()
                                    .requireProofKey(true)
                                    .requireAuthorizationConsent(false)
                                    .build()
                    )
                    .tokenSettings(
                            TokenSettings.builder()
                                    .accessTokenTimeToLive(
                                            Duration.ofMinutes(10)
                                    )
                                    .refreshTokenTimeToLive(
                                            Duration.ofDays(7)
                                    )
                                    .reuseRefreshTokens(false)
                                    .build()
                    )
                    .build();
            repository.save(client);
        };
    }
}
