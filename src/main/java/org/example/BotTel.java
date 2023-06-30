package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;
import java.util.List;
import java.util.*;
public class BotTel extends TelegramLongPollingBot {
    Integer biggestAPIRequest = 0;
    String countryAPIName = "ERROR";
    String countryAPIPopulation = "ERROR";
    String countryAPI = "ERROR";
    String catApi = "ERROR";
    String jokeAPIT = "ERROR";
    String numbersAPI = "ERROR";
    String quotesAPI = "ERROR";
    String quotesAPIAuthor = "ERROR";
    List<Object> languages;
    HashMap<Long, Integer> userID = new HashMap<>();
    HashMap<String, Integer> userNameCount = new HashMap<>();
    HashMap<String, Integer> APINameUseCount = new HashMap<String, Integer>(){{
        put("CatsAPI", 0);
        put("NumbersAPI", 0);
        put("QuotesAPI", 0);
        put("JokesAPI", 0);
        put("CountriesAPI", 0);
    }
    };
    private int requestCounter = 0;
    private int usersCounter = 0;
    Queue<String> lastMessages = new LinkedList<>();
    Window window = new Window();


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
        Date date = new Date();
        String userMessage;
        Long chatID;
        String userFirstName;

        if (update.hasCallbackQuery()) {
            chatID = update.getCallbackQuery().getMessage().getChatId();
            userMessage = update.getCallbackQuery().getData().toString();
            userFirstName = update.getCallbackQuery().getFrom().getFirstName();
        } else {
            chatID = update.getMessage().getChatId();
            userFirstName = update.getMessage().getFrom().getFirstName();
            userMessage = update.getMessage().getText();
        }
        addToLastTenMessages(userMessage + ". " + userFirstName + ". " + date+ "\n");
        window.lestMessages.message.setText("<html>last ten messages: "+lastTenMessagesString()+"</html>");
        if (!userNameCount.containsKey(userFirstName)){
            userNameCount.put(userFirstName, 0);
        }
        else {
            userNameCount.put(userFirstName, userNameCount.get(userFirstName) + 1);
        }
        Integer phase = this.userID.get(chatID);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        calculateMostActiveUser();
        if (phase == null && update.getMessage().getText().equals("/start")) {
            usersCounter++;
            window.botInfo.getUniqueUsers().setText("Amount of unique users: " + usersCounter);
            sendMessage.setText("What content would you like?(If you want to show it again write /restart)");

            InlineKeyboardButton numberFacts = new InlineKeyboardButton();
            numberFacts.setText("Numbers");
            numberFacts.setCallbackData("numbers");

            InlineKeyboardButton countryDetails = new InlineKeyboardButton();
            countryDetails.setText("Countries");
            countryDetails.setCallbackData("country");

            InlineKeyboardButton jokes = new InlineKeyboardButton();
            jokes.setText("Jokes");
            jokes.setCallbackData("joke");

            InlineKeyboardButton quotes = new InlineKeyboardButton();
            quotes.setText("Quotes");
            quotes.setCallbackData("quote");

            InlineKeyboardButton catFacts = new InlineKeyboardButton();
            catFacts.setText("Cat-Facts");
            catFacts.setCallbackData("cat");

            List<InlineKeyboardButton> topRow = new ArrayList<>();
            if (window.controlPanel.savedSelections.get("Jokes") == 1) {
                topRow.add(jokes);
            }
            if (window.controlPanel.savedSelections.get("Quotes") == 1) {
                topRow.add(quotes);
            }
            if (window.controlPanel.savedSelections.get("Countries") == 1) {
                topRow.add(countryDetails);
            }
            if (window.controlPanel.savedSelections.get("Numbers") == 1) {
                topRow.add(numberFacts);
            }
            if (window.controlPanel.savedSelections.get("Cat-Facts") == 1) {
                topRow.add(catFacts);
            }
            List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            this.userID.put(chatID,1);
        } else if (phase == 1) {
            String message = update.getCallbackQuery().getData();
            if (Objects.equals(message, "cat")) {
                window.botInfo.setCatFactsCount(window.botInfo.getCatFactsCount() + 1);
                window.botInfo.setChartConfig();
                catAPIRun();
                updateAPIRequestCounter();
                sleep();

                sendMessage.setText(catApi);
                this.APINameUseCount.put("CatsAPI", APINameUseCount.get("CatsAPI") + 1);
                this.userID.put(chatID, 0);
            }
            if (Objects.equals(message, "joke")) {
                window.botInfo.setJokesCount(window.botInfo.getJokesCount() + 1);
                window.botInfo.setChartConfig();
                jokeAPIRun("");
                updateAPIRequestCounter();
                sleep();

                sendMessage.setText(jokeAPIT);
                this.APINameUseCount.put("JokesAPI", APINameUseCount.get("JokesAPI") + 1);
                this.userID.put(chatID, 0);
            }
            if (Objects.equals(message, "numbers")){
                window.botInfo.setNumbersCount(window.botInfo.getNumbersCount() + 1);
                window.botInfo.setChartConfig();

                sendMessage.setText("Pick a number.");
                this.userID.put(chatID, 2);
            }
            if (Objects.equals(message, "quote")){
                window.botInfo.setQuotesCount(window.botInfo.getQuotesCount() + 1);
                window.botInfo.setChartConfig();
                quotesAPIRun();
                updateAPIRequestCounter();
                sleep();

                sendMessage.setText(quotesAPI);
                this.APINameUseCount.put("QuotesAPI", APINameUseCount.get("QuotesAPI") + 1);
                this.userID.put(chatID, 0);
            }
            if (Objects.equals(message, "country")){
                window.botInfo.setCountriesCount(window.botInfo.getCountriesCount() + 1);
                window.botInfo.setChartConfig();
                sendMessage.setText("What country? (Needs to be in code like IL/ISR=Israel)");
                this.userID.put(chatID, 3);
            }
        }
        else if (phase == 2){
            numbersAPIRun(update.getMessage().getText());
            updateAPIRequestCounter();
            sleep();

            sendMessage.setText(numbersAPI);
            this.APINameUseCount.put("NumbersAPI", APINameUseCount.get("NumbersAPI") + 1);
            this.userID.put(chatID, 0);
        } else if (phase == 3) {
            countriesAPIRun(update.getMessage().getText());
            updateAPIRequestCounter();
            sleep();

            sendMessage.setText("You chose "+ update.getMessage().getText() +". Now choose your desired information:");
            sendMessage.setText("The population: "+countryAPIPopulation+"\n The languages spoken: "+ languages.toString()+
                    "\nThe subregion is: "+countryAPI);
            this.APINameUseCount.put("CountriesAPI", APINameUseCount.get("CountriesAPI") + 1);
            this.userID.put(chatID, 0);
        }
        else if (phase == 0 && update.getMessage().getText().equals("/restart")){
            sendMessage.setText("What content would you like to see?");
            InlineKeyboardButton numberFacts = new InlineKeyboardButton();

            numberFacts.setText("Numbers");
            numberFacts.setCallbackData("numbers");

            InlineKeyboardButton countryDetails = new InlineKeyboardButton();
            countryDetails.setText("Countries");
            countryDetails.setCallbackData("country");

            InlineKeyboardButton jokes = new InlineKeyboardButton();
            jokes.setText("Jokes");
            jokes.setCallbackData("joke");

            InlineKeyboardButton quotes = new InlineKeyboardButton();
            quotes.setText("Quotes");
            quotes.setCallbackData("quote");

            InlineKeyboardButton catFacts = new InlineKeyboardButton();
            catFacts.setText("Cat-Facts");
            catFacts.setCallbackData("cat");

            List<InlineKeyboardButton> topRow = new ArrayList<>();
            if (window.controlPanel.savedSelections.get("Quotes") == 1) {
                topRow.add(quotes);
            }
            if (window.controlPanel.savedSelections.get("Countries") == 1) {
                topRow.add(countryDetails);
            }
            if (window.controlPanel.savedSelections.get("Numbers") == 1) {
                topRow.add(numberFacts);
            }
            if (window.controlPanel.savedSelections.get("Cat-Facts") == 1) {
                topRow.add(catFacts);
            }
            if (window.controlPanel.savedSelections.get("Jokes") == 1) {
                topRow.add(jokes);
            }
            List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

            this.userID.put(chatID,1);
        }
        send(sendMessage);
        calculateMostPopularAPI();
    }
    public void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void jokeAPIRun(String kind){
        new Thread(() -> {
            try {
                HttpResponse<String> response = Unirest.get("https://v2.jokeapi.dev/joke/Any" + kind).asString();
                ObjectMapper objectMapper = new ObjectMapper();
                JokeFilter jokeModel = objectMapper.readValue(response.getBody(), JokeFilter.class);
                if (jokeModel.getType().equals("twopart") ){ // If there is a punchline
                    this.jokeAPIT = jokeModel.getSetup()+ " " + jokeModel.getDelivery();
                }
                else {
                    this.jokeAPIT = jokeModel.getJoke();
                }
            } catch (Exception e){
                try {
                    throw new Exception("ERROR");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
    }
    public void catAPIRun() {
        new Thread(() -> {
            try {
                HttpResponse<String> response = Unirest.get("https://catfact.ninja/fact?max_length=140").asString();
                ObjectMapper objectMapper = new ObjectMapper();
                CatFactsFilter catModel = objectMapper.readValue(response.getBody(), CatFactsFilter.class);
                if (catModel != null) {
                    this.catApi = catModel.getFact();
                }
            } catch (Exception e){
                try {
                    throw new Exception("ERROR");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
    }
    public void numbersAPIRun(String number){
        new Thread(() ->{
            try {
                HttpResponse<String> response = Unirest.get("http://numbersapi.com/" + number).asString();
                if (response.getStatus() == 200){
                    this.numbersAPI = response.getBody();
                }
            } catch (Exception e){
                try {
                    throw new Exception("ERROR");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
    }
    public void quotesAPIRun(){
        new Thread(() ->{
            try {
                HttpResponse<String> response = Unirest.get("https://api.quotable.io/random").asString();
                ObjectMapper objectMapper = new ObjectMapper();
                QuotesFilter quotesModel = objectMapper.readValue(response.getBody(), QuotesFilter.class);
                this.quotesAPIAuthor = quotesModel.getAuthor();
                this.quotesAPI = quotesModel.getContent();
            }catch (Exception e){
                System.out.println("ERROR");
            }
        }).start();
    }
    public void countriesAPIRun(String name){
        new Thread(() ->{
            try {
                HttpResponse<String> response = Unirest.get("https://restcountries.com/v2/alpha/" + name).asString();
                ObjectMapper objectMapper = new ObjectMapper();
                CountrysFilter countryModel = objectMapper.readValue(response.getBody(), CountrysFilter.class);
                this.languages = countryModel.getLanguages();
                this.countryAPIPopulation = countryModel.getPopulation();
                this.countryAPIName = countryModel.getName();
                this.countryAPI = countryModel.getSubregion();
            } catch (Exception e){
                System.out.println("ERROR");
            }
        }).start();
    }
    public void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateAPIRequestCounter(){
        this.requestCounter++;
        window.botInfo.getRequests().setText("API requests: "+requestCounter);
    }
    public void calculateMostActiveUser(){
        int max = 0;
        String mostUser = "";
        for (String username : userNameCount.keySet()){
            if (userNameCount.get(username) > max){
                max = userNameCount.get(username);
            }
        }
        for (String username : userNameCount.keySet()){
            if (userNameCount.get(username) == max){
                mostUser = username;
            }
        }
        window.botInfo.getMostActiveUser().setText("Most active user: "+ mostUser);
    }
    public void calculateMostPopularAPI(){
        for (String api: this.APINameUseCount.keySet()){
            if (APINameUseCount.get(api) > biggestAPIRequest){
                biggestAPIRequest = APINameUseCount.get(api);
            }
        }
        for (String api : this.APINameUseCount.keySet()){
            if (APINameUseCount.get(api).equals(biggestAPIRequest)){
                window.botInfo.getMostPopularAPI().setText("Most popular API: "+ api);
            }
        }
    }
    public String lastTenMessagesString(){
        StringBuilder get = new StringBuilder();
        for (String message : lastMessages){
            get.append(message);
            get.append("<br><br>");
        }
        return get.toString();
    }
    public void addToLastTenMessages(String message){
        if (this.lastMessages.size() > 9){
            lastMessages.remove();
            lastMessages.add(message);
        }
        else {
            lastMessages.add(message);
        }
    }
}