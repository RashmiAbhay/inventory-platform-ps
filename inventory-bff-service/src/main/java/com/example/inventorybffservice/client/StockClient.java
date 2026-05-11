package com.example.inventorybffservice.client;




import com.example.inventorybffservice.dto.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockClient {

    private final RestClient restClient;

    @Value("${services.stock-service.base-url}")
    private String stockServiceBaseUrl;

    public List<StockResponse> getStocks(
            List<String> itemIds) {

        String itemList =
                String.join(",", itemIds);

        return restClient.get()
                .uri(stockServiceBaseUrl +
                        "/stocks?itemList=" + itemList)
                .retrieve()
                .body(
                        new ParameterizedTypeReference<List<StockResponse>>() {}
                );
    }
}
