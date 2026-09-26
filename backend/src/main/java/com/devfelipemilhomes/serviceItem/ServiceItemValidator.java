package com.devfelipemilhomes.serviceItem;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ServiceItemValidator {
    private final ServiceItemRepository repository;

    public ServiceItemValidator(ServiceItemRepository repository){
        this.repository = repository;
    }

    public void validate(ServiceItem serviceItem){
        if(repository.existsByName(serviceItem.getName())){
            ServiceItem serviceItemRepo = repository.findByName(serviceItem.getName());
            if(!Objects.equals(serviceItem.getId(), serviceItemRepo.getId())){
                throw new DuplicateFieldException("Service already registered");
            }
        }
    }
}
