package com.example.stockservice.exception;

public class InvalidStockRequestException extends RuntimeException {

    public InvalidStockRequestException(String message) {
        super(message);
    }
}