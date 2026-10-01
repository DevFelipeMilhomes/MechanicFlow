package com.devfelipemilhomes.professional;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.role.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class ProfessionalValidator {
    private final ProfessionalRepository repository;
    private final RoleRepository roleRepository;

    public ProfessionalValidator(ProfessionalRepository repository, RoleRepository roleRepository){
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public void validate(Professional professional, Set<Long> roles){
        if (repository.existsByCpf(professional.getCpf())){
            Professional professionalRepo = repository.findByCpf(professional.getCpf());
            if(!Objects.equals(professional.getId(), professionalRepo.getId())){
                throw new DuplicateFieldException("CPF already registered");
            }
        }

        if(roleRepository.findAllById(roles).size()!=roles.size()){
            throw new ResourceNotFoundException("One or more roles were not found.");
        }
    }
}
