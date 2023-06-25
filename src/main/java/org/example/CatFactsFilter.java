package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CatFactsFilter {
    private String fact;

    public String getFact() {
        return fact;
    }
}
