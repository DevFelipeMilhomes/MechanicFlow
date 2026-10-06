package com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.dto;

import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.ServiceOrderServiceItemId;
import jakarta.validation.constraints.NotNull;

public record ServiceOrderServiceItemRemoveDTO(
        @NotNull(message = "Required field")
        Long serviceItemId
) {
}
