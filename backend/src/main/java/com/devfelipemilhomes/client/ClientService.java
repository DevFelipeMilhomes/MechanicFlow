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

    public ClientService(ClientRepository repository, ClientValidator validator){
        this.repository = repository;
        this.validator = validator;
    }

    public ClientResponseDTO create(ClientRequestDTO dto){
        Client client = new Client();
        String cpf = dto.cpf()
                .replace(".", "")
                .replace("-", "")
                .replace(" ", "");
        client.setName(dto.name());
        client.setCpf(cpf);
        client.setPhone(dto.phone());
        client.setEmail(dto.email());

        validator.validate(client);

        repository.save(client);

        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getPhone(),
                client.getEmail(),
                client.getCreatedAt()
        );
    }

    public ClientResponseDTO findById(Long id){
        Client client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("client not found"));

        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getPhone(),
                client.getEmail(),
                client.getCreatedAt()
        );
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

        return clientsList.stream().map(
                c -> new ClientResponseDTO(
                        c.getId(),
                        c.getName(),
                        c.getCpf(),
                        c.getPhone(),
                        c.getEmail(),
                        c.getCreatedAt()
                )
        ).collect(Collectors.toList());

    }

    public ClientResponseDTO update(Long id,ClientRequestDTO dto){
        Client client = repository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("client not found")
        );
        String cpf = dto.cpf()
                .replace(".", "")
                .replace("-", "")
                .replace(" ", "");

        client.setName(dto.name());
        client.setCpf(cpf);
        client.setPhone(dto.phone());
        client.setEmail(dto.email());

        validator.validate(client);
        repository.save(client);

        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getPhone(),
                client.getEmail(),
                client.getCreatedAt()
        );
    }

}
