package com.apulbere.rhea.model.google;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private List<Items> items;
    private String totalItems;
    private String kind;
}
