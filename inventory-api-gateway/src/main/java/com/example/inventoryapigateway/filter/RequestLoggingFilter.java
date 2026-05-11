package com.example.inventoryapigateway.filter;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class RequestLoggingFilter
        implements GlobalFilter {
    private static final Logger log =
            LoggerFactory.getLogger(
                    RequestLoggingFilter.class);
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        log.info(
                "Incoming Request -> Method: {}, URI: {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI()
        );

        return chain.filter(exchange);
    }
}
