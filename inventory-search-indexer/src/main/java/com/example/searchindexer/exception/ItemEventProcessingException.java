package com.example.searchindexer.exception;

public class ItemEventProcessingException
        extends RuntimeException {

    public ItemEventProcessingException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}