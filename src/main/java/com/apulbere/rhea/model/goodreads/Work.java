package com.apulbere.rhea.model.goodreads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Work {
    @JsonProperty("best_book")
    private Book book;
}
