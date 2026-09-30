package com.devfelipemilhomes.client;

import com.devfelipemilhomes.client.dto.ClientRequestDTO;
import com.devfelipemilhomes.client.dto.ClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(
            target = "cpf",
            expression = "java(dto.cpf()\n" +
                    "                .replace(\".\", \"\")\n" +
                    "                .replace(\"-\", \"\")\n" +
                    "                .replace(\" \", \"\"))"
    )
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toResponse(Client client);

    @Mapping(
            target = "cpf",
            expression = "java(dto.cpf()\n" +
                    "                .replace(\".\", \"\")\n" +
                    "                .replace(\"-\", \"\")\n" +
                    "                .replace(\" \", \"\"))"
    )
    void toUpdate(
            ClientRequestDTO dto,
            @MappingTarget Client client
    );
}
