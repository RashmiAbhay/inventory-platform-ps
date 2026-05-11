package com.example.inventorybffservice.client;

import com.example.inventorybffservice.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemClient {

    private final RestClient restClient;

    @Value("${services.item-service.base-url}")
    private String itemServiceBaseUrl;

    public List<ItemResponse> getItems() {

        return restClient.get()
                .uri(itemServiceBaseUrl + "/items")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ItemResponse>>() {});
    }
}