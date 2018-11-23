package com.apulbere.rhea.service.enrich;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.model.Quote;
import com.apulbere.rhea.model.google.Book;
import com.apulbere.rhea.model.google.Items;
import com.apulbere.rhea.model.google.SearchResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ConditionalOnProperty("google.key")
@Primary
@Component
public class GoogleBooksEnrich implements QuoteEnrich {
    private WebClient googleBookWebClient;
    private String googleKey;

    public GoogleBooksEnrich(WebClient.Builder webClientBuilder, @Value("${google.key}") String googleKey) {
        this.googleBookWebClient = webClientBuilder.baseUrl("https://www.googleapis.com/books").build();
        this.googleKey = googleKey;
    }

    @Override
    public Mono<DetailedQuote> enrich(Quote quote) {
        return googleBookWebClient.get()
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
