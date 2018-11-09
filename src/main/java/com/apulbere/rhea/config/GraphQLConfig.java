package com.apulbere.rhea.config;

import com.apulbere.rhea.repository.QuoteDataFetcher;
import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import static graphql.GraphQL.newGraphQL;
import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@Configuration
public class GraphQLConfig {

    @Bean
    @SneakyThrows
    public GraphQL graphQL(@Value("classpath:models.graphqls") Resource resource, QuoteDataFetcher quoteRepository) {
        var typeDefinition = new SchemaParser().parse(resource.getFile());
        var wiring = runtimeWiring(quoteRepository);
        var schema = new SchemaGenerator().makeExecutableSchema(typeDefinition, wiring);
        return newGraphQL(schema).build();
    }

    private RuntimeWiring runtimeWiring(QuoteDataFetcher quoteDataFetcher) {
        return newRuntimeWiring().type("Query", tw -> tw.dataFetcher("quotes", quoteDataFetcher)).build();
    }
}
