package com.example.inventorybffservice.exception;

public class StockServiceUnavailableException
        extends RuntimeException {

    public StockServiceUnavailableException(
            String message) {

        super(message);
    }
}