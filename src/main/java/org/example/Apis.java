package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class Apis  {
    private BotTel bot;
 private HttpResponse<String> jokes;
 private JokeFilter jokeFilter;
 private String jokeStart;
 private String jokeEnd;

    public String getJokeStart() {
        return jokeStart;
    }

    public String getJokeEnd() {
        return jokeEnd;
    }

    private ObjectMapper objectMapper;
 public Apis() throws Exception{
      jokes = Unirest.get("https://official-joke-api.appspot.com/jokes/random").asString();
      objectMapper = new ObjectMapper();
     jokeFilter= objectMapper.readValue(jokes.getBody(), JokeFilter.class);
     this.jokeStart = jokeFilter.getSetup();
     bot.setJokeAPI1(jokeFilter.getSetup());
 }
}
