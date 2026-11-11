package com.devfelipemilhomes.serviceOrder.exception;

public class ReservationNotYetReturned extends RuntimeException {
    public ReservationNotYetReturned(String message) {
        super(message);
    }
}
