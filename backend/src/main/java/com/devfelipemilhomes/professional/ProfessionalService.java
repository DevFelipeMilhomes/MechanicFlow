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

    public ProfessionalService( ProfessionalRepository repository, ProfessionalValidator validator){
        this.repository = repository;
        this.validator = validator;
    }

    public ProfessionalResponseDTO create(ProfessionalRequestDTO dto){
        Professional professional = new Professional();
        String cpf = dto.cpf()
                        .replace(".", "")
                        .replace("-", "")
                        .replace(" ", "");
        professional.setName(dto.name());
        professional.setCpf(cpf);
        professional.setPhone(dto.phone());
        professional.setEmail(dto.email());

        validator.validate(professional);

        repository.save(professional);

        return new ProfessionalResponseDTO(
                professional.getId(),
                professional.getName(),
                professional.getCpf(),
                professional.getPhone(),
                professional.getEmail(),
                professional.getCreatedAt()
        );
    }

    public ProfessionalResponseDTO findById(Long id){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));

        return new ProfessionalResponseDTO(
                professional.getId(),
                professional.getName(),
                professional.getCpf(),
                professional.getPhone(),
                professional.getEmail(),
                professional.getCreatedAt()
        );
    }

    public ProfessionalResponseDTO update(Long id, ProfessionalRequestDTO dto){
        Professional professional = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional not found"));
        String cpf = dto.cpf()
                .replace(".", "")
                .replace("-", "")
                .replace(" ", "");
        professional.setName(dto.name());
        professional.setCpf(cpf);
        professional.setPhone(dto.phone());
        professional.setEmail(dto.email());

        validator.validate(professional);
        repository.save(professional);

        return new ProfessionalResponseDTO(
                professional.getId(),
                professional.getName(),
                professional.getCpf(),
                professional.getPhone(),
                professional.getEmail(),
                professional.getCreatedAt()
        );
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

        return professionalList.stream().map(
                p -> new ProfessionalResponseDTO(
                        p.getId(),
                        p.getName(),
                        p.getCpf(),
                        p.getPhone(),
                        p.getEmail(),
                        p.getCreatedAt()
                )
        ).collect(Collectors.toList());
    }
}
