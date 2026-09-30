package com.devfelipemilhomes.serviceItem;

import com.devfelipemilhomes.serviceItem.dto.ServiceItemRequestDTO;
import com.devfelipemilhomes.serviceItem.dto.ServiceItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceItemMapper {
    @Mapping(
            target = "name",
            expression = "java(dto.name()\n" +
                    "                .toLowerCase())"
    )
    ServiceItem toEntity(ServiceItemRequestDTO dto);

    ServiceItemResponseDTO toResponse(ServiceItem serviceItem);

    @Mapping(
            target = "name",
            expression = "java(dto.name()\n" +
                    "                .toLowerCase())"
    )
    void toUpdate(
            ServiceItemRequestDTO dto,
            @MappingTarget ServiceItem serviceItem
    );
}
