package com.devfelipemilhomes.appUser;

import com.devfelipemilhomes.appUser.dto.UserResponseDTO;
import com.devfelipemilhomes.appUser.exception.UsernameAlreadyExistsException;
import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.appUser.dto.UserRequestDTO;
import com.devfelipemilhomes.professional.Professional;
import com.devfelipemilhomes.professional.ProfessionalRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;
    private final ProfessionalRepository professionalRepository;

    public UserService(
            UserRepository repository,
            PasswordEncoder encoder,
            ProfessionalRepository professionalRepository,
            UserMapper mapper
            ){
        this.repository = repository;
        this.encoder = encoder;
        this.professionalRepository = professionalRepository;
        this.mapper = mapper;
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto){
        Professional professional = professionalRepository.findById(dto.ProfessionalId()).orElseThrow(
                ()-> new ResourceNotFoundException("Professional not found")
        );

        if (repository.existsByUsername(normalizeUsername(dto.username()))) {
            throw new UsernameAlreadyExistsException(
                    "Username already registered"
            );
        }

        String password = encoder.encode(dto.password());
        AppUser user = mapper.toEntity(dto, password, professional);
        repository.save(user);
        return mapper.toResponse(user);
    }

    public UserResponseDTO findByUsername(String username){
        AppUser user = repository.findByUsername(normalizeUsername(username))
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return mapper.toResponse(user);
    }

    public UserResponseDTO findById(Long id){
        AppUser user = repository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return mapper.toResponse(user);
    }

    public String normalizeUsername(String username){
        return username
                .trim()
                .toLowerCase(Locale.ROOT);
    }
}
