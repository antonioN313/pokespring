package com.antonio.pokespring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2";
    
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(POKEAPI_BASE_URL)
                .build();
    }
}