package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountrysFilter {
    private String name;
    private String subregion;
   private String population;
   private List<Map<String, Object>> languages;
    public List<Object> getLanguages() {
        List<Object> langs = new ArrayList<>();
        for (Map language : languages){
            langs.add(language.get("name"));
        }
        return langs;
    }
    public String getName() {
        return name;
    }
    public String getSubregion() {
        return subregion;
    }
    public String getPopulation() {
        return population;
    }
}
