package com.devfelipemilhomes.client;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClientValidator {
    private final ClientRepository repository;

    public ClientValidator(ClientRepository repository){ this.repository = repository; }

    public void validate(Client client){
        if (repository.existsByCpf(client.getCpf())){
            Client clientRepo = repository.findByCpf(client.getCpf());
            if(!Objects.equals(client.getId(), clientRepo.getId())) {
                throw new DuplicateFieldException("CPF already registered");
            }
        }
    }
}
