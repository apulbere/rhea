package com.apulbere.rhea.model.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Items {
    @JsonProperty("volumeInfo")
    private Book book;
}