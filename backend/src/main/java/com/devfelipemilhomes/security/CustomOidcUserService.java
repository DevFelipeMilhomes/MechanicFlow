package com.devfelipemilhomes.security;

import com.devfelipemilhomes.appUser.AppUserIdentityService;
import com.devfelipemilhomes.appUser.dto.AuthenticatedAppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomOidcUserService
        implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final AppUserIdentityService appUserIdentityService;

    private final OidcUserService delegate = new OidcUserService();

    public CustomOidcUserService(AppUserIdentityService appUserIdentityService) {
        this.appUserIdentityService = appUserIdentityService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {

        OidcUser googleUser = delegate.loadUser(userRequest);

        String googleSub = googleUser.getSubject();
        String email = googleUser.getEmail();

        Boolean emailVerified =
                googleUser.getClaimAsBoolean("email_verified");

        AuthenticatedAppUser appUser =
                appUserIdentityService.resolveGoogleUser(
                        googleSub,
                        email,
                        Boolean.TRUE.equals(emailVerified)
                );

        Set<GrantedAuthority> authorities = appUser.roles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toUnmodifiableSet());

        return new CustomOidcUser(
                authorities,
                googleUser.getIdToken(),
                googleUser.getUserInfo(),
                appUser.userId(),
                appUser.professionalId(),
                appUser.roles()
        );
    }
}