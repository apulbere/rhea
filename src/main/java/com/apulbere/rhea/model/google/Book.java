package com.apulbere.rhea.model.google;

import lombok.Data;

import java.util.List;

@Data
public class Book {
    private String pageCount;
    private List<String> authors;
    private String title;
    private String averageRating;
    private String ratingsCount;
    private String description;
    private String subtitle;
    private List<String> categories;
    private String publishedDate;
    private String publisher;
}
