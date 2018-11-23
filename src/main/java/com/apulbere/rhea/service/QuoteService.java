package com.apulbere.rhea.service;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.repository.QuoteReactiveRepository;
import com.apulbere.rhea.service.enrich.QuoteEnrich;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class QuoteService {
    private QuoteReactiveRepository quoteReactiveRepository;
    private QuoteEnrich quoteEnrich;

    public Mono<DetailedQuote> findOneDetailed(String id) {
        return quoteReactiveRepository.findById(id).flatMap(quoteEnrich::enrich);
    }
}
