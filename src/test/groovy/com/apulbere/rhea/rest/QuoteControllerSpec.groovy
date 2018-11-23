package com.apulbere.rhea.rest

import com.apulbere.rhea.model.Quote
import com.apulbere.rhea.repository.QuoteReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteControllerSpec extends Specification {

    @Autowired WebTestClient webTestClient
    @Autowired QuoteReactiveRepository quoteReactiveRepository

    def "it saves quotes"() {
        given:
            def quotes = [
                    new Quote(source: UUID.randomUUID().toString(), author: "a1", text: "txt1"),
                    new Quote(source: UUID.randomUUID().toString(), author: "a2", text: "txt2")
            ]
        when: "request is made with $quotes"
            webTestClient.post().uri("/quotes/bulk")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Flux.fromIterable(quotes), Quote.class)
                .exchange()
                .expectStatus()
                .isOk()
        then: "we have in database sent quotes"
            def dbQuotes = quoteReactiveRepository.findAll().collectList().block()
            dbQuotes.size() == 2
            dbQuotes[0].id != null
            dbQuotes[1].id != null
            dbQuotes.collect({it.id = null; it}) == quotes
    }
}
