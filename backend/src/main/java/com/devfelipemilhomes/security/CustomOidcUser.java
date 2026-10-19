package com.devfelipemilhomes.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Collection;
import java.util.List;

public final class CustomOidcUser extends DefaultOidcUser {

    private final Long appUserId;
    private final Long professionalId;
    private final List<String> roleNames;

    public CustomOidcUser(
            Collection<? extends GrantedAuthority> authorities,
            OidcIdToken idToken,
            OidcUserInfo userInfo,
            Long appUserId,
            Long professionalId,
            List<String> roleNames) {

        super(authorities, idToken, userInfo, "sub");

        this.appUserId = appUserId;
        this.professionalId = professionalId;
        this.roleNames = List.copyOf(roleNames);
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public Long getProfessionalId() {
        return professionalId;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }
}
