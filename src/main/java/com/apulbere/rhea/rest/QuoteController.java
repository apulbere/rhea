package com.apulbere.rhea.rest;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.model.Quote;
import com.apulbere.rhea.repository.QuoteReactiveRepository;
import com.apulbere.rhea.service.QuoteService;
import graphql.GraphQL;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.Validator;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private GraphQL graphQL;
    private QuoteReactiveRepository quoteReactiveRepository;
    private QuoteService quoteService;
    private Validator validator;

    @GetMapping("/{id}")
    public Mono<DetailedQuote> findOne(@PathVariable String id) {
        return quoteService.findOneDetailed(id);
    }

    @GetMapping("/top")
    public Flux<Quote> findTop(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return quoteReactiveRepository.findAll(PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "likes")));
    }

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
    public Flux<Quote> bulkInsert(@RequestBody Flux<Quote> quotes) {
        return quoteReactiveRepository.insert(quotes.filter(quote -> validator.validate(quote).isEmpty()));
    }
}
