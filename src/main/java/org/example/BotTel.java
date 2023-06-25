package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotTel extends TelegramLongPollingBot {
    private Map<Long,Integer> phasesMap;
    private String choice1;
    private String choice2;
    private String choice3;
    private int counter;
    private String jokeAPI1;
    private String jokeAPI2;
    private Apis apiInfo;


    private String catFactsAPI;
    private String countriesAPI;

    public void setJokeAPI1(String jokeAPI1) {
        this.jokeAPI1 = jokeAPI1;
    }

    private String numberFactsAPI;
    private String quoteAPI1;
    private String quoteAPI2;

    private InlineKeyboardButton choiceButton2;
    private static int count=0;

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public BotTel(String choice1, String choice2, String choice3){
        this.phasesMap = new HashMap<>();
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        try {
            HttpResponse<String> jokes = Unirest.get("https://official-joke-api.appspot.com/jokes/random").asString();
            ObjectMapper objectMapper = new ObjectMapper();
            JokeFilter jokeFilter= objectMapper.readValue(jokes.getBody(), JokeFilter.class);
            HttpResponse<String> cats = Unirest.get("https://catfact.ninja/fact?max_length=200").asString();
            HttpResponse<String> numbers = Unirest.get("http://numbersapi.com/random/trivia").asString();
            HttpResponse<String> quotes= Unirest.get("https://api.quotable.io/random").asString();
            QuotesFilter quotesFilter = objectMapper.readValue(quotes.getBody(),QuotesFilter.class);
            CatFactsFilter catFactsFilter = objectMapper.readValue(cats.getBody(),CatFactsFilter.class);
            this.catFactsAPI = catFactsFilter.getFact();
            this.quoteAPI1 = quotesFilter.getContent();
            this.quoteAPI2 = quotesFilter.getAuthor();
            this.numberFactsAPI = numbers.getBody();
            this.jokeAPI1 = jokeFilter.getSetup();
            this.jokeAPI2 = jokeFilter.getPunchline();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getBotUsername() {
        return "FeelBoredbot";
    }

    @Override
    public String getBotToken() {
        return "5887121031:AAEwgpzgm9IMX8oF5y6gllRvPglUkDtcN-Y";
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatID;
        if (update.hasCallbackQuery()){ // button was clicked
            chatID = update.getCallbackQuery().getMessage().getChatId();
        }
        else {// massage sent
            chatID = update.getMessage().getChatId();
        }
        Integer phase = this.phasesMap.get(chatID);
        SendMessage sendMessage = new SendMessage();
        SendMessage sendMessage1 = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage1.setChatId(chatID);

        if (phase == null){
            count++;
            sendMessage.setText("What content would you like to see?");
            InlineKeyboardButton choiceButton1 = new InlineKeyboardButton();
            choiceButton1.setText(choice1);
            choiceButton1.setCallbackData(choice1);

            choiceButton2 = new InlineKeyboardButton();
            choiceButton2.setText(choice2);
            choiceButton2.setCallbackData(choice2);

            InlineKeyboardButton choiceButton3 = new InlineKeyboardButton();
            choiceButton3.setText(choice3);
            choiceButton3.setCallbackData(choice3);

            List<InlineKeyboardButton>topRow = List.of(choiceButton1, choiceButton2,choiceButton3);

            List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);


            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            sendMessage1.setReplyMarkup(inlineKeyboardMarkup);

            //never been spoken with bot
            this.phasesMap.put(chatID,1);
        }else {
            if (phase == 1){
                String callbackData = update.getCallbackQuery().getData();
                if (callbackData.equals(choice1)){
                    if (choice1 == "Jokes"){
                        sendMessage.setText(this.jokeAPI1);
                        sendMessage1.setText(this.jokeAPI2);
                        this.phasesMap.put(chatID,3);
                    } else if (choice1 == "Cat-Facts") {
                        sendMessage.setText(this.catFactsAPI);
                        this.phasesMap.put(chatID,3);
                    } else if (choice1 == "Countries") {
                        sendMessage.setText("Which country? (In code like ISR = Israel)");
                        this.phasesMap.put(chatID,2);
                    } else if (choice1 == "Number") {
                        sendMessage.setText(this.numberFactsAPI);
                        this.phasesMap.put(chatID,3);
                    } else if (choice1 == "Quotes") {
                        sendMessage.setText(this.quoteAPI1);
                        sendMessage1.setText("By "+this.quoteAPI2);
                        this.phasesMap.put(chatID,3);
                    }
                }else if (callbackData.equals(choice2)){
                    if (choice2 == "Jokes"){
                        sendMessage.setText(this.jokeAPI1);
                        sendMessage1.setText(this.jokeAPI2);
                        this.phasesMap.put(chatID,3);
                    } else if (choice2 == "Cat-Facts") {
                        sendMessage.setText(this.catFactsAPI);
                        this.phasesMap.put(chatID,3);
                    } else if (choice2 == "Countries") {
                        sendMessage.setText("Which country? (In code like ISR = Israel)");
                        this.phasesMap.put(chatID,2);
                    } else if (choice2 == "Number") {
                        sendMessage.setText(this.numberFactsAPI);
                        this.phasesMap.put(chatID,3);
                    } else if (choice2 == "Quotes") {
                        sendMessage.setText(this.quoteAPI1);
                        sendMessage1.setText("By "+this.quoteAPI2);
                        this.phasesMap.put(chatID,3);
                    }
                }else {
                    if (choice3 == "Jokes"){
                        sendMessage.setText(this.jokeAPI1);
                        sendMessage1.setText(this.jokeAPI2);
                        this.phasesMap.put(chatID,3);
                    } else if (choice3 == "Cat-Facts") {
                        sendMessage.setText(this.catFactsAPI);
                        this.phasesMap.put(chatID,3);
                    } else if (choice3 == "Countries") {
                        sendMessage.setText("Which country? (In code like ISR = Israel)");
                        this.phasesMap.put(chatID,2);
                    } else if (choice3 == "Number") {
                        sendMessage.setText(this.numberFactsAPI);
                        this.phasesMap.put(chatID,3);
                    } else if (choice3 == "Quotes") {
                        sendMessage.setText(this.quoteAPI1);
                        sendMessage1.setText("By "+this.quoteAPI2);
                        this.phasesMap.put(chatID,3);
                    }
                }
            }
            if (phase ==3){
                sendMessage.setText("Would you like to continue?");
                InlineKeyboardButton yes = new InlineKeyboardButton();
                yes.setText("Yes");
                yes.setCallbackData("Yes");
                InlineKeyboardButton no = new InlineKeyboardButton();
                no.setText("No");
                no.setCallbackData("No");

                List<InlineKeyboardButton>topRow = List.of(yes,no);

                List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(keyboard);


                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                sendMessage1.setReplyMarkup(inlineKeyboardMarkup);
                this.phasesMap.put(chatID,4);
            }
            if (phase == 2){
                try {
                    String text = update.getMessage().getText();
                    HttpResponse<String> countries = Unirest.get("https://restcountries.com/v2/alpha/"+text).asString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    CountrysFilter countrysFilter = objectMapper.readValue(countries.getBody(), CountrysFilter.class);
                    if (countrysFilter.getName() == null){
                        sendMessage.setText("Sorry try again");
                        this.phasesMap.put(chatID,2);
                    }else {
                        sendMessage.setText("The "+ countrysFilter.getName()+"'s capital is:"+countrysFilter.getCapital()+
                                " and population is:"+countrysFilter.getPopulation());
                        for (String neighbor : countrysFilter.getBorders()){
                            countries = Unirest.get("https://restcountries.com/v2/alpha/"+neighbor).asString();
                            countrysFilter = objectMapper.readValue(countries.getBody(),CountrysFilter.class);
                            sendMessage1.setText("The neighbors are:"+countrysFilter.getName()+" and the population of the neighbors:"+countrysFilter.getPopulation());
                            this.phasesMap.put(chatID,3);
                        }
                    }
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            if (phase == 4){
                String callbackData = update.getCallbackQuery().getData();
                if (callbackData.equals("Yes")){
                    this.phasesMap.put(chatID,null);
                }else {
                    sendMessage.setText("Good bye, we will be ready for your back.");
                    this.phasesMap.put(chatID,5);
                    count--;
                }
            }
            if (phase==5){
                if (update.getMessage().getText().equals("/start")){
                    this.phasesMap.put(chatID,null);
                }
            }

//            phase++;
//            this.phasesMap.put(chatID,phase);
        }
        send(sendMessage);
        send(sendMessage1);
    }

    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onClosing(){
        count--;
    }
    public void setTextNo(String name) {
        choiceButton2 = new InlineKeyboardButton();
        choiceButton2.setText(name);
    }
}