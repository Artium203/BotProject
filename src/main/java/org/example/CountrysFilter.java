package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountrysFilter {
    private String name;
    private String capital;
    private int population;
    private List<String> borders;

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }

    public List<String> getBorders() {
        return borders;
    }
}
