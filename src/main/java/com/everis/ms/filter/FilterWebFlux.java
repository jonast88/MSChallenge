package com.everis.ms.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Flow;

@Component
@Slf4j
public class FilterWebFlux implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startMillis = System.currentTimeMillis();


        //exchange.getResponse().getHeaders().add("startTime",String.valueOf(startMillis));
        //ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header("initTime",String.valueOf(startMillis)).build();
        //ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        ServerHttpResponse response = exchange.getResponse();

        response.beforeCommit(() -> {
            response.getHeaders().set("X-Duration",String.format("%s ms",String.valueOf(System.currentTimeMillis()-startMillis)));
            return Mono.empty();
        });

        return chain.filter(exchange.mutate().response(response).build())
                .doOnSuccess(aVoid ->{
                    log.info("Time {}ms", System.currentTimeMillis() - startMillis);
                });

    }
}