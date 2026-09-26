package com.devfelipemilhomes.part;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.part.dto.PartRequestDTO;
import com.devfelipemilhomes.part.dto.PartResponseDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartSevice {
    private final PartRepository repository;
    private final PartValidation validation;

    public PartSevice(PartRepository repository, PartValidation validation){
        this.repository = repository;
        this.validation = validation;
    }

    public PartResponseDTO create(PartRequestDTO dto){
        Part part = new Part();
        part.setName(dto.name()
                .toLowerCase());
        part.setDescription(dto.description());
        part.setUnitPrice(dto.unitPrice());

        validation.validate(part);
        repository.save(part);

        return new PartResponseDTO(
                part.getId(),
                part.getName(),
                part.getDescription(),
                part.getUnitPrice(),
                part.getCreatedAt()
        );
    }

    public PartResponseDTO findById(Long id){
        Part part = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Part not found"));
        return new PartResponseDTO(
                part.getId(),
                part.getName(),
                part.getDescription(),
                part.getUnitPrice(),
                part.getCreatedAt()
        );
    }

    public void update(Long id, PartRequestDTO dto){
        Part part = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Part not found"));
        part.setName(dto.name()
                .toLowerCase());
        part.setDescription(dto.description());
        part.setUnitPrice(dto.unitPrice());

        validation.validate(part);
        repository.save(part);

    }

    public void delete(Long id){
        Part part = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Part not found"));
        repository.delete(part);
    }

    public List<PartResponseDTO> searchByExample(String name, String description, BigDecimal unitPrice){
        Part part = new Part();
        part.setName(name);
        part.setDescription(description);
        part.setUnitPrice(unitPrice);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("id","createdAt")
                .withMatcher(
                        "name",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                )
                .withMatcher(
                        "description",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                )
                .withMatcher(
                        "unitPrice",
                        ExampleMatcher.GenericPropertyMatchers.exact()
                );

        Example<Part> partExample = Example.of(part, matcher);

        List<Part> partList = repository.findAll(partExample);

        return partList.stream().map(
                p-> new PartResponseDTO(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getUnitPrice(),
                        p.getCreatedAt()
                )
        ).collect(Collectors.toList());
    }
}
