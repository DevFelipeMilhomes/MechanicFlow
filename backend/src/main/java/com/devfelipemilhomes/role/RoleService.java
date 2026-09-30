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
    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, RoleValidator validator, RoleMapper mapper){
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    public RoleResponseDTO create(RoleRequestDTO dto){
        Role role = mapper.toEntity(dto);

        validator.validate(role);
        repository.save(role);

        return mapper.toResponse(role);
    }

    public RoleResponseDTO findById(Long id){
        Role role = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        return mapper.toResponse(role);
    }

    public void update(Long id, RoleRequestDTO dto){
        Role role = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        mapper.toUpdate(dto, role);
        validator.validate(role);
        repository.save(role);
    }

    public List<RoleResponseDTO> findAll(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public void delete(Long id){
        Role role = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        repository.delete(role);
    }
}
