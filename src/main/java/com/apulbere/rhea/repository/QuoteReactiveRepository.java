package com.apulbere.rhea.repository;

import com.apulbere.rhea.model.Quote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteReactiveRepository extends ReactiveMongoRepository<Quote, String>, QuoteRepository {
}
