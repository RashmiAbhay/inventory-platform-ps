package com.example.inventorybffservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemServiceUnavailableException.class)
    public ResponseEntity<?> handleItemServiceException(ItemServiceUnavailableException ex) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("timestamp", LocalDateTime.now(),

                "message", ex.getMessage()));
    }

    @ExceptionHandler(StockServiceUnavailableException.class)
    public ResponseEntity<?> handleStockServiceException(StockServiceUnavailableException ex) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("timestamp", LocalDateTime.now(),

                "message", ex.getMessage()));
    }
}