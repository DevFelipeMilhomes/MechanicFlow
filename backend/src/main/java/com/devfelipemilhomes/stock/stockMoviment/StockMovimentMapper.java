package com.devfelipemilhomes.stock.stockMoviment;

import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.ServiceOrderPart;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.dto.ServiceOrderPartSummaryDTO;
import com.devfelipemilhomes.stock.Stock;
import jakarta.annotation.Nullable;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StockMovimentMapper {
    public StockMoviment toEntry(Stock stock, Integer quantity){
        Part part = stock.getPart();
        StockMoviment stockMoviment = new StockMoviment();
        stockMoviment.setStock(stock);
        stockMoviment.setMovimentType(MovimentType.ENTRY);
        stockMoviment.setQuantity(quantity);
        stockMoviment.setPartName(part.getName());
        stockMoviment.setUnitPrice(part.getUnitPrice());
        String description = String.format(
                "Entry from the stock of '%s'.",
                stockMoviment.getPartName()
        );
        stockMoviment.setDescription(description);

        return stockMoviment;
    }

    public StockMoviment toAdjustment(Stock stock, Integer quantity){
        Part part = stock.getPart();
        StockMoviment stockMoviment = new StockMoviment();
        stockMoviment.setStock(stock);
        stockMoviment.setMovimentType(MovimentType.ADJUSTMENT);
        stockMoviment.setQuantity(quantity);
        stockMoviment.setPartName(part.getName());
        stockMoviment.setUnitPrice(part.getUnitPrice());
        String description = String.format(
                "Adjustment from the stock of '%s'.",
                stockMoviment.getPartName()
        );
        stockMoviment.setDescription(description);

        return stockMoviment;
    }

    public StockMoviment toReservation(Stock stock, Integer quantity, @Nullable ServiceOrderPart serviceOrderPart){
        Part part = stock.getPart();
        StockMoviment stockMoviment = new StockMoviment();
        stockMoviment.setStock(stock);
        stockMoviment.setMovimentType(MovimentType.RESERVATION);
        stockMoviment.setQuantity(quantity);
        stockMoviment.setPartName(part.getName());
        stockMoviment.setUnitPrice(part.getUnitPrice());
        if(serviceOrderPart!=null){
            stockMoviment.setServiceOrderPart(serviceOrderPart);
        }
        String description = String.format(
                "Reservation from the stock of '%s'.",
                stockMoviment.getPartName()
        );
        stockMoviment.setDescription(description);

        return stockMoviment;
    }

    public StockMoviment toConsumption(Stock stock, Integer quantity, @Nullable ServiceOrderPart serviceOrderPart){
        Part part = stock.getPart();
        StockMoviment stockMoviment = new StockMoviment();
        stockMoviment.setStock(stock);
        stockMoviment.setMovimentType(MovimentType.CONSUMPTION);
        stockMoviment.setQuantity(quantity);
        stockMoviment.setPartName(part.getName());
        stockMoviment.setUnitPrice(part.getUnitPrice());
        if(serviceOrderPart!=null){
            stockMoviment.setServiceOrderPart(serviceOrderPart);
        }
        String description = String.format(
                "Consumption from the stock of '%s'.",
                stockMoviment.getPartName()
        );
        stockMoviment.setDescription(description);

        return stockMoviment;
    }

    public StockMoviment toRelease(Stock stock, Integer quantity, @Nullable ServiceOrderPart serviceOrderPart){
        Part part = stock.getPart();
        StockMoviment stockMoviment = new StockMoviment();
        stockMoviment.setStock(stock);
        stockMoviment.setMovimentType(MovimentType.RELEASE);
        stockMoviment.setQuantity(quantity);
        stockMoviment.setPartName(part.getName());
        stockMoviment.setUnitPrice(part.getUnitPrice());
        if(serviceOrderPart!=null){
            stockMoviment.setServiceOrderPart(serviceOrderPart);
        }
        String description = String.format(
                "Release from the stock of '%s'.",
                stockMoviment.getPartName()
        );
        stockMoviment.setDescription(description);

        return stockMoviment;
    }

    public StockMovimentResponseDTO toResponse(StockMoviment stockMoviment){
        Long serviceOrderPartId = stockMoviment.getServiceOrderPart() != null
                ? stockMoviment.getServiceOrderPart().getId()
                : null;

        return new StockMovimentResponseDTO(
                stockMoviment.getId(),
                stockMoviment.getStock().getId(),
                serviceOrderPartId,
                stockMoviment.getPartName(),
                stockMoviment.getUnitPrice(),
                stockMoviment.getMovimentType(),
                stockMoviment.getQuantity(),
                stockMoviment.getDescription(),
                stockMoviment.getCreatedAt()
        );
    }
}
