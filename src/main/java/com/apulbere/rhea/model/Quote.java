package com.apulbere.rhea.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quote")
public class Quote {
    @Wither
    private String id;
    private String source;
    private String author;
    @NotEmpty
    private String text;
    private long likes;
}
