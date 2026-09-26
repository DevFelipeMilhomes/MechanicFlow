package com.devfelipemilhomes.part;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PartValidation {
    private final PartRepository repository;

    public PartValidation(PartRepository repository){
        this.repository = repository;
    }

    public void validate(Part part){
        if(repository.existsByName(part.getName())){
            Part partRepo = repository.findByName(part.getName());
            if(!Objects.equals(part.getId(), partRepo.getId())){
                throw new DuplicateFieldException("Part already registered");
            }
        }
    }
}
