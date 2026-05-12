package com.example.inventorybffservice.exception;

public class ItemServiceUnavailableException
        extends RuntimeException {

    public ItemServiceUnavailableException(
            String message) {

        super(message);
    }
}