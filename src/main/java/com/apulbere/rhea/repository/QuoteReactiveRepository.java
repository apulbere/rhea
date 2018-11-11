package com.apulbere.rhea.repository;

import com.apulbere.rhea.model.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface QuoteReactiveRepository extends ReactiveMongoRepository<Quote, String>, QuoteRepository {

    @Query("{ id: { $exists: true }}")
    Flux<Quote> findAll(Pageable pageable);
}
