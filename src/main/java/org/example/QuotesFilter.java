package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesFilter {
    private String content;

    private String author;

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }


}
