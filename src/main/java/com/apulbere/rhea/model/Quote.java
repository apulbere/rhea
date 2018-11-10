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
    protected String id;
    protected String source;
    protected String author;
    @NotEmpty
    protected String text;
    protected long likes;
}
