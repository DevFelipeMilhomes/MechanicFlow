package com.devfelipemilhomes.serviceOrder.exception;

public class StatusReturnNotAllowed extends RuntimeException {
    public StatusReturnNotAllowed(String message) {
        super(message);
    }
}
