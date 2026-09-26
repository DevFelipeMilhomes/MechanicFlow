package com.devfelipemilhomes.role;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleValidator {
    private final RoleRepository repository;

    public RoleValidator(RoleRepository repository){
        this.repository = repository;
    }

    public void validate(Role role){
        if(repository.existsByName(role.getName())){
            Role roleRepo = repository.findByName(role.getName());
            if(!Objects.equals(role.getId(), roleRepo.getId())){
                throw new DuplicateFieldException("Role already registered");
            }
        }
    }
}
