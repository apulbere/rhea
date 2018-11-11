package com.apulbere.rhea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient googleBooksClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("https://www.googleapis.com/books").build();
    }
}
