package com.example.inventorybffservice.config;

import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final ObservationRegistry observationRegistry;

    @Bean
    RestClient restClient() {

        return RestClient.builder()
                .observationRegistry(observationRegistry)
                .build();
    }
}