package com.apulbere.rhea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class RheaApp {

	public static void main(String[] args) {
		SpringApplication.run(RheaApp.class, args);
	}
}
