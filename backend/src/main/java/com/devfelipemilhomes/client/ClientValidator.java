package com.devfelipemilhomes.client;

import com.devfelipemilhomes.client.exception.DuplicateCpfException;
import org.springframework.stereotype.Component;

@Component
public class ClientValidator {
    private final ClientRepository repository;

    public ClientValidator(ClientRepository repository){ this.repository = repository; }

    public void validate(Client client){
        if (repository.existsByCpf(client.getCpf())){
            throw new DuplicateCpfException("CPF already registered");
        }
    }
}
