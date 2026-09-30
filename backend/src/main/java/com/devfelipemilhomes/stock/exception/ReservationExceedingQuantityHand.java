package com.devfelipemilhomes.stock.exception;

public class ReservationExceedingQuantityHand extends RuntimeException {
    public ReservationExceedingQuantityHand(String message) {
        super(message);
    }
}
