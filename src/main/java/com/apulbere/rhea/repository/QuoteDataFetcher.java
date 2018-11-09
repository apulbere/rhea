package com.apulbere.rhea.repository;

import com.apulbere.rhea.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class QuoteDataFetcher implements DataFetcher<List<Quote>> {

    private QuoteReactiveRepository quoteRepository;
    private ObjectMapper objectMapper;

    @Override
    public List<Quote> get(DataFetchingEnvironment environment) {
        Quote quote = objectMapper.convertValue(environment.getArguments(), Quote.class);
        return quoteRepository.findAll(Example.of(quote)).collectList().block();
    }


}
