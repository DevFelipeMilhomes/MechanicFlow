package com.devfelipemilhomes.stock.exception;

public class QuantityReleaseExceedingUnconsumed extends RuntimeException {
    public QuantityReleaseExceedingUnconsumed(String message) {
        super(message);
    }
}
