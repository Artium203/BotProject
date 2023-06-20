package org.example;

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
    private InlineKeyboardButton no;
    private static int count=0;

    public BotTel(String choice1,String choice2,String choice3){
        this.phasesMap = new HashMap<>();
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;

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
        sendMessage.setChatId(chatID);
        if (phase == null){
            sendMessage.setText("Do you?");
            InlineKeyboardButton yes = new InlineKeyboardButton();
            yes.setText(choice1);
            yes.setCallbackData("Y");

            no = new InlineKeyboardButton();
            no.setText(choice2);
            no.setCallbackData("N");

            List<InlineKeyboardButton>topRow = List.of(yes,no);

            List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);


            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

            //never been spoken with bot
            this.phasesMap.put(chatID,1);
        }else {
            if (phase == 1){
                String callbackData = update.getCallbackQuery().getData();
                if (callbackData.equals("Y")){
                    sendMessage.setText("What is your E");
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
        no.setText(name);
    }
}
