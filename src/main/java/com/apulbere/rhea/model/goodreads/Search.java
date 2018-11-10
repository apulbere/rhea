package com.apulbere.rhea.model.goodreads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {
    @JacksonXmlElementWrapper(localName = "results")
    @JsonProperty("work")
    private List<Work> results;
}
