package com.devfelipemilhomes.professional;

import com.devfelipemilhomes.professional.dto.ProfessionalRequestDTO;
import com.devfelipemilhomes.professional.dto.ProfessionalResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfessionalMapper {
    @Mapping(
            target = "cpf",
            expression = "java(dto.cpf()\n" +
                    "                .replace(\".\", \"\")\n" +
                    "                .replace(\"-\", \"\")\n" +
                    "                .replace(\" \", \"\"))"
    )
    Professional toEntity(ProfessionalRequestDTO dto);

    ProfessionalResponseDTO toResponse(Professional professional);

    @Mapping(
            target = "cpf",
            expression = "java(dto.cpf()\n" +
                    "                .replace(\".\", \"\")\n" +
                    "                .replace(\"-\", \"\")\n" +
                    "                .replace(\" \", \"\"))"
    )
    void toUpdate(
            ProfessionalRequestDTO dto,
            @MappingTarget Professional professional
    );
}
