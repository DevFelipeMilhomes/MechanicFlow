package com.devfelipemilhomes.security.authorizationServer;

import com.devfelipemilhomes.security.CustomOidcUser;
import com.devfelipemilhomes.security.MechanicFlowPrincipal;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    public JWKSource<SecurityContext> jwkSource(
            @Value("${app.security.jwk.keystore-location}")
            Resource keystoreResource,
            @Value("${app.security.jwk.keystore-password}")
            String keystorePassword,
            @Value("${app.security.jwk.key-alias}")
            String keyAlias
    ){
        char[] password = keystorePassword.toCharArray();
        try(InputStream input = keystoreResource.getInputStream()) {

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(input, password);

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, password);

            Certificate certificate = keyStore.getCertificate(keyAlias);

            RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();

            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey((RSAPrivateKey) privateKey)
                    .keyID(keyAlias)
                    .build();

            return new ImmutableJWKSet<>(new JWKSet(rsaKey));

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to load the Authorization Server keys.",
                    e
            );
        }
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {

        return context -> {

            if (!OAuth2TokenType.ACCESS_TOKEN.equals(
                    context.getTokenType())) {
                return;
            }

            Object principal = context.getPrincipal().getPrincipal();

            if (principal instanceof MechanicFlowPrincipal user) {

                String appUserId =
                        Objects.toString(user.getAppUserId());

                Object professionalId =
                        user.getProfessionalId();

                List<String> roles =
                        user.getRoleNames();

                context.getClaims()
                        .subject(appUserId)
                        .claim("app_user_id", appUserId)
                        .claim("professional_id", professionalId)
                        .claim("roles", roles);
            } else {
                throw new IllegalStateException(
                        "Principal incompatible with MechanicFlowPrincipal"
                );
            }
        };
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter scopeConverter =
                new JwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            Set<GrantedAuthority> authorities = new HashSet<>();

            Collection<GrantedAuthority> scopeAuthorities =
                    scopeConverter.convert(jwt);

            if (scopeAuthorities != null) {
                authorities.addAll(scopeAuthorities);
            }

            List<String> roles =
                    jwt.getClaimAsStringList("roles");

            if (roles != null) {
                roles.stream()
                        .filter(StringUtils::hasText)
                        .map(String::trim)
                        .map(role -> role.startsWith("ROLE_")
                                ? role
                                : "ROLE_" + role)
                        .map(SimpleGrantedAuthority::new)
                        .forEach(authorities::add);
            }

            return authorities;
        });

        return converter;
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(
            @Value("${app.security.issuer}") String issuer
    ){
        return AuthorizationServerSettings.builder()
                .issuer(issuer)
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(
            @Value("${app.security.issuer}") String issuer
    ){
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withJwkSetUri(issuer + "/oauth2/jwks")
                .build();
        decoder.setJwtValidator(
                JwtValidators.createDefaultWithIssuer(issuer)
        );
        return decoder;
    }
}
