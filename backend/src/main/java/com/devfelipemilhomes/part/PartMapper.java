package com.devfelipemilhomes.part;

import com.devfelipemilhomes.part.dto.PartRequestDTO;
import com.devfelipemilhomes.part.dto.PartResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PartMapper {
    @Mapping(
            target = "name",
            expression = "java(dto.name()\n" +
                    "                .toLowerCase())"
    )
    Part toEntity(PartRequestDTO dto);

    PartResponseDTO toResponse(Part part);

    @Mapping(
            target = "name",
            expression = "java(dto.name()\n" +
                    "                .toLowerCase())"
    )
    void toUpdate(
            PartRequestDTO dto,
            @MappingTarget Part part
    );
}
