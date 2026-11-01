package com.devfelipemilhomes.security.authorizationServer;

import com.devfelipemilhomes.security.CustomOidcUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.jackson.SecurityJacksonModules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
public class AuthorizationServerPersistenceConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate){
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }


    @Bean
    OAuth2AuthorizationService oAuth2AuthorizationService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {

        JdbcOAuth2AuthorizationService service =
                new JdbcOAuth2AuthorizationService(
                        jdbcTemplate,
                        registeredClientRepository
                );

        JsonMapper jsonMapper = authorizationJsonMapper();

        var rowMapper =
                new JdbcOAuth2AuthorizationService
                        .JsonMapperOAuth2AuthorizationRowMapper(
                        registeredClientRepository,
                        jsonMapper
                );

        service.setAuthorizationRowMapper(rowMapper);

        return service;
    }

    private JsonMapper authorizationJsonMapper() {

        ClassLoader classLoader =
                JdbcOAuth2AuthorizationService.class.getClassLoader();

        BasicPolymorphicTypeValidator.Builder validator =
                BasicPolymorphicTypeValidator.builder()

                        // Autoriza seu principal customizado.
                        .allowIfSubType(CustomOidcUser.class)

                        // Autoriza as implementações imutáveis
                        // de coleções do próprio JDK.
                        .allowIfSubType("java.util.ImmutableCollections$");

        return JsonMapper.builder()
                .addModules(
                        SecurityJacksonModules.getModules(
                                classLoader,
                                validator
                        )
                )
                .build();
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository
    ){
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }
}
