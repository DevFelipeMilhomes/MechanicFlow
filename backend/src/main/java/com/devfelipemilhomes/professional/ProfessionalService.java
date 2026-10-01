package com.devfelipemilhomes.professional;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.professional.dto.ProfessionalRequestDTO;
import com.devfelipemilhomes.professional.dto.ProfessionalResponseDTO;
import com.devfelipemilhomes.role.RoleRepository;
import com.devfelipemilhomes.role.dto.RoleAssignRequestDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalService {
    private final ProfessionalRepository repository;
    private final ProfessionalValidator validator;
    private final ProfessionalMapper mapper;
    private final RoleRepository roleRepository;

    public ProfessionalService( ProfessionalRepository repository, ProfessionalValidator validator, ProfessionalMapper mapper, RoleRepository roleRepository){
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
    }

    public ProfessionalResponseDTO create(ProfessionalRequestDTO dto){
        Professional professional = mapper.toEntity(dto);
        validator.validate(professional, dto.roleIds());
        professional.setRoles(new HashSet<>(roleRepository.findAllById(dto.roleIds())));
        repository.save(professional);
        return mapper.toResponse(professional);
    }

    public ProfessionalResponseDTO findById(Long id){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));

        return mapper.toResponse(professional);
    }

    public void update(Long id, ProfessionalRequestDTO dto){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));
        mapper.toUpdate(dto, professional);
        professional.setRoles(new HashSet<>(roleRepository.findAllById(dto.roleIds())));
        validator.validate(professional, dto.roleIds());
        repository.save(professional);
    }

    public void delete(Long id){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));
        repository.delete(professional);
    }

    public void assignRole(Long id, RoleAssignRequestDTO dto){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));
        professional.getRoles().add(roleRepository.findById(dto.roleId()).orElseThrow(()->new ResourceNotFoundException("Role not found")));
        repository.save(professional);
    }

    public void disassociateRole(Long id, Long roleId){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));
        professional.getRoles().remove(roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role not found")));
        repository.save(professional);
    }

    public List<ProfessionalResponseDTO> searchByExample(String name, String cpf, String phone, String email){
        Professional professional = new Professional();
        professional.setName(name);
        professional.setCpf(cpf);
        professional.setPhone(phone);
        professional.setEmail(email);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("id","createdAt")
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

        Example<Professional> professionalExample = Example.of(professional, matcher);

        List<Professional> professionalList = repository.findAll(professionalExample);

        return professionalList.stream().map(mapper::toResponse).toList();
    }
}
