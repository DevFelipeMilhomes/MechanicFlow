package com.devfelipemilhomes.role;

import com.devfelipemilhomes.role.dto.RoleRequestDTO;
import com.devfelipemilhomes.role.dto.RoleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(
            target = "name",
            expression = "java(dto.name()\n" +
                    "                .toUpperCase())"
    )
    Role toEntity(RoleRequestDTO dto);

    RoleResponseDTO toResponse(Role role);

    @Mapping(
            target = "name",
            expression = "java(dto.name()\n" +
                    "                .toUpperCase())"
    )
    void toUpdate(
            RoleRequestDTO dto,
            @MappingTarget Role role
    );
}
