package com.devfelipemilhomes.role;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.role.dto.RoleRequestDTO;
import com.devfelipemilhomes.role.dto.RoleResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository repository;
    private final RoleValidator validator;

    public RoleService(RoleRepository repository, RoleValidator validator){
        this.repository = repository;
        this.validator = validator;
    }

    public RoleResponseDTO create(RoleRequestDTO dto){
        Role role = new Role();
        role.setName(dto.name()
                .toUpperCase());
        role.setDescription(dto.description());

        validator.validate(role);
        repository.save(role);

        return new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }

    public RoleResponseDTO findById(Long id){
        Role role = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        return new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }

    public void update(Long id, RoleRequestDTO dto){
        Role role = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        role.setName(dto.name()
                .toUpperCase());
        role.setDescription(dto.description());
        validator.validate(role);
        repository.save(role);
    }

    public List<RoleResponseDTO> findAll(){
        return repository.findAll().stream().map(
                role -> new RoleResponseDTO(
                        role.getId(),
                        role.getName(),
                        role.getDescription()
                )
        ).collect(Collectors.toList());
    }

    public void delete(Long id){
        Role role = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        repository.delete(role);
    }
}
