package com.devfelipemilhomes.appUser;

import com.devfelipemilhomes.appUser.dto.AuthenticatedAppUser;
import com.devfelipemilhomes.professional.Professional;
import com.devfelipemilhomes.role.Role;
import jakarta.transaction.Transactional;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class AppUserIdentityService {

    private final UserRepository appUserRepository;

    public AppUserIdentityService(UserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public AuthenticatedAppUser resolveGoogleUser(
            String googleSub,
            String email,
            boolean emailVerified) {

        if (!StringUtils.hasText(googleSub)) {
            throw new OAuth2AuthenticationException("Invalid google identity");
        }

        AppUser userWithGoogleSub = appUserRepository
                .findByGoogleSub(googleSub)
                .orElse(null);

        if (userWithGoogleSub == null) {

            if (!emailVerified || !StringUtils.hasText(email)) {
                throw new OAuth2AuthenticationException("Unverified email");
            }

            String normalizedEmail = email
                    .trim()
                    .toLowerCase(Locale.ROOT);

            AppUser userWithEmail = appUserRepository
                    .findByEmailIgnoreCase(normalizedEmail)
                    .orElseThrow(() -> new OAuth2AuthenticationException(
                            "User not registered"
                    ));

            if (StringUtils.hasText(userWithEmail.getGoogleSub())
                    && !userWithEmail.getGoogleSub().equals(googleSub)) {
                throw new OAuth2AuthenticationException("Google account already linked");
            }

            userWithEmail.setGoogleSub(googleSub);
            appUserRepository.save(userWithEmail);
        }

        AppUser appUser = appUserRepository
                .findByGoogleSub(googleSub)
                .orElse(null);

        assert appUser != null;
        Professional professional = appUser.getProfessional();


        List<String> roles = professional.getRoles()
                .stream()
                .map(Role::getName)
                .filter(StringUtils::hasText)
                .map(role -> role.trim().toUpperCase(Locale.ROOT))
                .map(role -> role.startsWith("ROLE_")
                        ? role.substring("ROLE_".length())
                        : role)
                .filter(StringUtils::hasText)
                .distinct()
                .toList();

        if (roles.isEmpty()) {
            throw new OAuth2AuthenticationException(
                    "User without roles"
            );
        }

        return new AuthenticatedAppUser(
                appUser.getId(),
                professional.getId(),
                roles
        );
    }

}