package com.apulbere.rhea.service;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.model.Quote;
import com.apulbere.rhea.model.google.Book;
import com.apulbere.rhea.model.google.Items;
import com.apulbere.rhea.model.google.SearchResult;
import com.apulbere.rhea.repository.QuoteReactiveRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class QuoteService {
    private QuoteReactiveRepository quoteReactiveRepository;
    private WebClient openLibraryWebClient;
    private String googleKey;

    public QuoteService(QuoteReactiveRepository quoteReactiveRepository,
                        WebClient openLibraryWebClient,
                        @Value("${google.key}") String googleKey) {
        this.quoteReactiveRepository = quoteReactiveRepository;
        this.openLibraryWebClient = openLibraryWebClient;
        this.googleKey = googleKey;
    }

    public Mono<DetailedQuote> findOneDetailed(String id) {
        return quoteReactiveRepository.findById(id).flatMap(this::findFirstBook);
    }

    private Mono<DetailedQuote> findFirstBook(Quote quote) {
        return openLibraryWebClient.get()
                .uri("/v1/volumes?q=intitle:{title}&key={key}", quote.getSource(), googleKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SearchResult.class)
                .map(searchResult -> new DetailedQuote(quote, convertGoogleBooksResponse(searchResult)));
    }

    @SneakyThrows
    private Book convertGoogleBooksResponse(SearchResult searchResult) {
        return searchResult.getItems().stream().findAny().map(Items::getBook).orElse(null);
    }
}
