package com.devfelipemilhomes.security;

import com.devfelipemilhomes.appUser.AppUser;
import com.devfelipemilhomes.appUser.UserRepository;
import com.devfelipemilhomes.role.Role;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(normalizeUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        List<String> roles = user.getProfessional()
                .getRoles()
                .stream()
                .map(Role::getName)
                .map(role -> role.trim().toUpperCase(Locale.ROOT))
                .distinct()
                .toList();

        return User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles.toArray(String[]::new))
                .disabled(!user.isEnabled())
                .build();

    }

    public String normalizeUsername(String username){
        return username
                .trim()
                .toLowerCase(Locale.ROOT);
    }
}
