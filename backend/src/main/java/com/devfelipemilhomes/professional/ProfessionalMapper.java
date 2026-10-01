package com.devfelipemilhomes.professional;

import com.devfelipemilhomes.professional.dto.ProfessionalRequestDTO;
import com.devfelipemilhomes.professional.dto.ProfessionalResponseDTO;
import com.devfelipemilhomes.role.Role;
import com.devfelipemilhomes.role.dto.RoleSummaryDTO;
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
    @Mapping(target = "roles", ignore = true)
    Professional toEntity(ProfessionalRequestDTO dto);

    ProfessionalResponseDTO toResponse(Professional professional);

    RoleSummaryDTO toRoleSummaryDTO(Role role);

    @Mapping(
            target = "cpf",
            expression = "java(dto.cpf()\n" +
                    "                .replace(\".\", \"\")\n" +
                    "                .replace(\"-\", \"\")\n" +
                    "                .replace(\" \", \"\"))"
    )
    @Mapping(target = "roles", ignore = true)
    void toUpdate(
            ProfessionalRequestDTO dto,
            @MappingTarget Professional professional
    );
}
