package com.devfelipemilhomes.professional;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProfessionalValidator {
    private final ProfessionalRepository repository;

    public ProfessionalValidator(ProfessionalRepository repository){
        this.repository = repository;
    }

    public void validate(Professional professional){
        if (repository.existsByCpf(professional.getCpf())){
            Professional professionalRepo = repository.findByCpf(professional.getCpf());
            if(!Objects.equals(professional.getId(), professionalRepo.getId())){
                throw new DuplicateFieldException("CPF already registered");
            }
        }
    }
}
