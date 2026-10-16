package com.devfelipemilhomes.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Long professionalId;
    private final String username;
    private final String password;
    private final List<String> roles;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Long professionalId,
            String username,
            String password,
            List<String> roles
    ) {
        this.professionalId = professionalId;
        this.username = username;
        this.password = password;
        this.roles = List.copyOf(roles);

        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    public Long getProfessionalId() {
        return professionalId;
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
