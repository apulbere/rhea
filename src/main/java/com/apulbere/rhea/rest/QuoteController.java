package com.apulbere.rhea.rest;

import com.apulbere.rhea.model.Quote;
import com.apulbere.rhea.repository.QuoteReactiveRepository;
import graphql.GraphQL;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private GraphQL graphQL;
    private QuoteReactiveRepository quoteReactiveRepository;

    @PostMapping
    public Mono<Quote> save(@RequestBody @Valid Quote quote) {
        return quoteReactiveRepository.save(quote.withId(null));
    }

    @PutMapping("/{id}")
    public Mono<Quote> update(@PathVariable String id, @RequestBody @Valid Quote quote) {
        return quoteReactiveRepository.merge(quote.withId(id));
    }

    @PutMapping("/{id}/like")
    public Mono<Quote> like(@PathVariable String id) {
        return quoteReactiveRepository.like(id);
    }

    @GetMapping
    public Flux<Quote> findAll() {
        return quoteReactiveRepository.findAll();
    }

    @PostMapping(value = "/query")
    public ResponseEntity query(@RequestBody String query){
        return ok(graphQL.execute(query).getData());
    }

    @PostMapping(value = "/bulk", consumes = APPLICATION_STREAM_JSON_VALUE)
    public Mono<Long> bulkInsert(@RequestBody Flux<Quote> quotes) {
        return quoteReactiveRepository.insert(quotes).count();
    }
}
