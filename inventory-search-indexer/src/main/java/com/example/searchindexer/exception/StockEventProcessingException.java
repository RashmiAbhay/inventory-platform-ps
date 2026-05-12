package com.example.searchindexer.exception;

public class StockEventProcessingException
        extends RuntimeException {

    public StockEventProcessingException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}