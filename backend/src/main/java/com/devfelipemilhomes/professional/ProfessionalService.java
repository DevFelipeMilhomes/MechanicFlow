package com.devfelipemilhomes.professional;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.professional.dto.ProfessionalRequestDTO;
import com.devfelipemilhomes.professional.dto.ProfessionalResponseDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalService {
    private final ProfessionalRepository repository;
    private final ProfessionalValidator validator;
    private final ProfessionalMapper mapper;

    public ProfessionalService( ProfessionalRepository repository, ProfessionalValidator validator, ProfessionalMapper mapper){
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    public ProfessionalResponseDTO create(ProfessionalRequestDTO dto){
        Professional professional = mapper.toEntity(dto);
        validator.validate(professional);
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

        validator.validate(professional);
        repository.save(professional);
    }

    public void delete(Long id){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));
        repository.delete(professional);
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
