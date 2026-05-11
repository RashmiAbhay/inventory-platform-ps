package com.example.inventoryapigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/inventory")
    public String inventoryFallback() {

        return """
                Inventory Service is
                temporarily unavailable
                """;
    }
}
