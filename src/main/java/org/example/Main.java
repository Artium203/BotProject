package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        window.opened();


//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new BotTel());
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
    }
}