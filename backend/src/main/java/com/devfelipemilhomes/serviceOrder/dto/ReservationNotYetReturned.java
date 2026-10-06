package com.devfelipemilhomes.serviceOrder.dto;

public class ReservationNotYetReturned extends RuntimeException {
    public ReservationNotYetReturned(String message) {
        super(message);
    }
}
