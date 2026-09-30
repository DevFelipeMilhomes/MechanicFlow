package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import com.devfelipemilhomes.stock.exception.ReservationExceedingQuantityHand;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockValidator {
    private final StockRepository repository;

    public StockValidator(StockRepository repository){
        this.repository = repository;
    }

    public void validate(Stock stock){
        if(repository.existsByPart(stock.getPart())){
            Stock stockRepo = repository.findByPart(stock.getPart());
            if(!Objects.equals(stock.getId(),stockRepo.getId())){
                throw new DuplicateFieldException("Part already registered in stock");
            }
        }

        if(stock.getQuantityReserved()>stock.getQuantityOnHand()){
            throw new ReservationExceedingQuantityHand("reservations exceeding the existing quantity");
        }
    }
}
