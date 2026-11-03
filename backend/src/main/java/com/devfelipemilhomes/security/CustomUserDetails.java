package com.devfelipemilhomes.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails, MechanicFlowPrincipal {

    private final Long appUserId;
    private final Long professionalId;
    private final String username;
    private final String password;
    private final List<String> roleNames;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Long professionalId,
            Long appUserId,
            String username,
            String password,
            List<String> roleNames
    ) {
        this.professionalId = professionalId;
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.roleNames = List.copyOf(roleNames);

        this.authorities = roleNames.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @JsonCreator
    public static CustomUserDetails restore(
            @JsonProperty("professionalId") Long professionalId,
            @JsonProperty("appUserId") Long appUserId,
            @JsonProperty("username") String username,
            @JsonProperty("roleNames") List<String> roleNames
    ) {
        return new CustomUserDetails(
                professionalId,
                appUserId,
                username,
                null,
                roleNames
        );
    }


    public Long getProfessionalId() {
        return professionalId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
