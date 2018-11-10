package com.apulbere.rhea.service;

import com.apulbere.rhea.model.DetailedQuote;
import com.apulbere.rhea.model.Quote;
import com.apulbere.rhea.model.goodreads.Book;
import com.apulbere.rhea.model.goodreads.GoodreadsResponse;
import com.apulbere.rhea.model.goodreads.Work;
import com.apulbere.rhea.repository.QuoteReactiveRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
    private XmlMapper xmlMapper;
    private String goodreadsKey;

    public QuoteService(QuoteReactiveRepository quoteReactiveRepository,
                        WebClient openLibraryWebClient,
                        @Value("${goodreads.key}") String goodreadsKey) {
        this.quoteReactiveRepository = quoteReactiveRepository;
        this.openLibraryWebClient = openLibraryWebClient;
        this.xmlMapper = new XmlMapper();
        this.goodreadsKey = goodreadsKey;
    }

    public Mono<DetailedQuote> findOneDetailed(String id) {
        return quoteReactiveRepository.findById(id).flatMap(this::findFirstBook);
    }

    private Mono<DetailedQuote> findFirstBook(Quote quote) {
        return openLibraryWebClient.get()
                .uri("/search.xml?key={key}&q={source}", goodreadsKey, quote.getSource())
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .map(goodreadsResponse -> new DetailedQuote(quote, convertGoodreadsResponse(goodreadsResponse)));
    }

    @SneakyThrows
    private Book convertGoodreadsResponse(String value) {
        GoodreadsResponse goodreadsResponse = xmlMapper.readValue(value, GoodreadsResponse.class);
        return goodreadsResponse.getSearch().getResults().stream().findAny().map(Work::getBook).orElse(null);
    }
}
