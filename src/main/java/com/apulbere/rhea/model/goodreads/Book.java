package com.apulbere.rhea.model.goodreads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String title;
    private Author author;
}
