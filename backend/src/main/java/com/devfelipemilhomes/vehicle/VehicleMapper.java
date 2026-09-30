package com.devfelipemilhomes.vehicle;

import com.devfelipemilhomes.vehicle.dto.VehicleRequestDTO;
import com.devfelipemilhomes.vehicle.dto.VehicleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    @Mapping(
            target = "plate",
            expression = "java(dto.plate()\n" +
                    "                .replace(\"-\", \"\")\n" +
                    "                .replace(\" \", \"\")\n" +
                    "                .toUpperCase())"
    )
    Vehicle toEntity(VehicleRequestDTO dto);

    VehicleResponseDTO toResponse(Vehicle vehicle);

    @Mapping(
            target = "plate",
            expression = "java(dto.plate()\n" +
                    "                .replace(\"-\", \"\")\n" +
                    "                .replace(\" \", \"\")\n" +
                    "                .toUpperCase())"
    )
    void toUpdate(
            VehicleRequestDTO dto,
            @MappingTarget Vehicle vehicle
    );
}
