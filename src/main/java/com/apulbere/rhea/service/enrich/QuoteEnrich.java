package com.apulbere.rhea.service.enrich;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.model.Quote;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface QuoteEnrich {
    Mono<DetailedQuote> enrich(Quote quote);
}
