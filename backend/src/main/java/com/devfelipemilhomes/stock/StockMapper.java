package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.part.dto.PartSummaryDTO;
import com.devfelipemilhomes.stock.dto.StockRequestDTO;
import com.devfelipemilhomes.stock.dto.StockResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {
    @Mapping(target = "part", ignore = true)
    Stock toEntity(StockRequestDTO dto);

    StockResponseDTO toResponse(Stock stock);

    PartSummaryDTO toPartSummary(Part part);
}
