package com.example.stockservice.exception;

public class EmptyItemListException extends RuntimeException {

    public EmptyItemListException(String message) {
        super(message);
    }
}