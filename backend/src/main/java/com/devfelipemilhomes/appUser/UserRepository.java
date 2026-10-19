package com.devfelipemilhomes.appUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmailIgnoreCase(String email);
    boolean existsByUsername(String username);
    Optional<AppUser> findByGoogleSub(String googleSub);
}
