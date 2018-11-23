package com.apulbere.rhea.service.enrich;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.model.Quote;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultQuoteEnrich implements QuoteEnrich {

    @Override
    public Mono<DetailedQuote> enrich(Quote quote) {
        return Mono.just(new DetailedQuote(quote));
    }
}
