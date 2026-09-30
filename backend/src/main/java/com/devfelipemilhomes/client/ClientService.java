package com.devfelipemilhomes.client;

import com.devfelipemilhomes.client.dto.ClientRequestDTO;
import com.devfelipemilhomes.client.dto.ClientResponseDTO;
import com.devfelipemilhomes.exception.ResourceNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final ClientValidator validator;
    private final ClientMapper mapper;

    public ClientService(ClientRepository repository, ClientValidator validator, ClientMapper mapper){
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    public ClientResponseDTO create(ClientRequestDTO dto){
        Client client = mapper.toEntity(dto);
        validator.validate(client);
        repository.save(client);
        return mapper.toResponse(client);
    }

    public ClientResponseDTO findById(Long id){
        Client client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("client not found"));

        return mapper.toResponse(client);
    }

    public void delete(Long id){
        Client client = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("client not found"));
        repository.delete(client);
    }

    public List<ClientResponseDTO> searchByExample(String name, String cpf, String phone, String email){
        Client client = new Client();
        client.setName(name);
        client.setCpf(cpf);
        client.setPhone(phone);
        client.setEmail(email);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("id", "createdAt")
                .withMatcher(
                        "name",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                )
                .withMatcher(
                        "cpf",
                        ExampleMatcher.GenericPropertyMatchers.exact()
                )
                .withMatcher(
                        "phone",
                        ExampleMatcher.GenericPropertyMatchers.exact()
                )
                .withMatcher(
                        "email",
                        ExampleMatcher.GenericPropertyMatchers.exact()
                );

        Example<Client> clientExample = Example.of(client,matcher);

        List<Client> clientsList = repository.findAll(clientExample);

        return clientsList.stream().map(mapper::toResponse).toList();

    }

    public void update(Long id,ClientRequestDTO dto){
        Client client = repository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("client not found")
        );
        mapper.toUpdate(dto,client);
        validator.validate(client);
        repository.save(client);
    }

}
