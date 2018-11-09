package com.apulbere.rhea.repository;

import com.apulbere.rhea.model.Quote;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@AllArgsConstructor
public class QuoteRepositoryImpl implements QuoteRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public Mono<Quote> like(String id) {
        var idQuery = new Query(where("id").is(id));
        mongoTemplate.updateFirst(idQuery, new Update().inc("likes", 1), Quote.class);
        return Mono.justOrEmpty(mongoTemplate.findOne(idQuery, Quote.class));
    }

    @Override
    public Mono<Quote> merge(Quote quote) {
        var idQuery = new Query(where("id").is(quote.getId()));

        Update update = new Update();
        update.set("source", quote.getSource());
        update.set("author", quote.getAuthor());
        update.set("text", quote.getText());

        mongoTemplate.upsert(idQuery, update, Quote.class);
        return Mono.justOrEmpty(mongoTemplate.findOne(idQuery, Quote.class));
    }
}
