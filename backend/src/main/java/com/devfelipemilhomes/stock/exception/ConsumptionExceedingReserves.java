package com.devfelipemilhomes.stock.exception;

public class ConsumptionExceedingReserves extends RuntimeException {
    public ConsumptionExceedingReserves(String message) {
        super(message);
    }
}
