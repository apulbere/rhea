package com.apulbere.rhea.model;

import com.apulbere.rhea.model.google.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DetailedQuote extends Quote {
    private Book book;

    public DetailedQuote(Quote quote) {
        this(quote, null);
    }

    public DetailedQuote(Quote quote, Book book) {
        this.id = quote.id;
        this.text = quote.text;
        this.likes = quote.likes;
        this.book = book;
    }

    @JsonIgnore
    @Override
    public String getSource() {
        return super.getSource();
    }

    @JsonIgnore
    @Override
    public String getAuthor() {
        return super.getAuthor();
    }
}
