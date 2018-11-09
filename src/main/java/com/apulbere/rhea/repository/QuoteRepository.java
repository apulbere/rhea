package com.apulbere.rhea.repository;

import com.apulbere.rhea.model.Quote;
import reactor.core.publisher.Mono;

public interface QuoteRepository {
    Mono<Quote> like(String id);
    Mono<Quote> merge(Quote quote);
}
