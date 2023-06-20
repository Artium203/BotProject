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
    private String jokeAPI1;
    private String jokeAPI2;
    private Apis apiInfo;

    public void setJokeAPI2(String jokeAPI2) {
        this.jokeAPI2 = jokeAPI2;
    }

    private String catFactsAPI;
    private String weatherAPI;

    public void setJokeAPI1(String jokeAPI1) {
        this.jokeAPI1 = jokeAPI1;
    }

    public void setCatFactsAPI(String catFactsAPI) {
        this.catFactsAPI = catFactsAPI;
    }

    public void setWeatherAPI(String weatherAPI) {
        this.weatherAPI = weatherAPI;
    }

    public void setFixerAPI(String fixerAPI) {
        this.fixerAPI = fixerAPI;
    }

    public void setNasaAPI(String nasaAPI) {
        this.nasaAPI = nasaAPI;
    }

    private String fixerAPI;
    private String nasaAPI;

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
        }else {// massage sent
            chatID = update.getMessage().getChatId();
        }
        Integer phase = this.phasesMap.get(chatID);
        SendMessage sendMessage = new SendMessage();
        SendMessage sendMessage1 = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage1.setChatId(chatID);

        if (phase == null){
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
                    }
                }else {
                    sendMessage.setText("Have a bless");
                }
//                String message = update.getMessage().getText();
//                if (message.equals("No")){
//                    sendMessage.setText("Have a bless");
//                }else {
//                    sendMessage.setText("What is your E");
//                }
            }
            phase++;
            this.phasesMap.put(chatID,phase);
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