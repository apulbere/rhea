package com.apulbere.rhea.config;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.service.enrich.QuoteEnrich;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class Beans {

    @Bean
    public QuoteEnrich defaultQuoteEnrich() {
        return quote -> Mono.just(new DetailedQuote(quote));
    }
}
