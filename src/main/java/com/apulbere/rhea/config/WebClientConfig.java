package com.apulbere.rhea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient openLibraryWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("https://www.goodreads.com").build();
    }
}
