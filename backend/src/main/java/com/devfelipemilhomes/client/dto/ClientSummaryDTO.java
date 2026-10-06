package com.devfelipemilhomes.client.dto;

public record ClientSummaryDTO(
        Long id,
        String name,
        String cpf,
        String phone
) {
}
