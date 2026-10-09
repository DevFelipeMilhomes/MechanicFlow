package com.devfelipemilhomes.appUser;

import com.devfelipemilhomes.appUser.dto.UserRequestDTO;
import com.devfelipemilhomes.appUser.dto.UserResponseDTO;
import com.devfelipemilhomes.professional.Professional;
import com.devfelipemilhomes.role.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class UserMapper {
    public AppUser toEntity(UserRequestDTO dto, String password, Professional professional){
        AppUser user = new AppUser();
        user.setUsername(dto.username()
                .trim()
                .toLowerCase(Locale.ROOT));
        user.setPassword(password);
        user.setProfessional(professional);
        return user;
    }

    public UserResponseDTO toResponse(AppUser user){
        List<String> roles = user.getProfessional()
                .getRoles()
                .stream()
                .map(Role::getName)
                .map(role -> role.trim().toUpperCase(Locale.ROOT))
                .toList();
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.isEnabled(),
                user.getProfessional().getId(),
                roles,
                user.getCreatedAt()
        );
    }
}
