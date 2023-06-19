package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    private App app;
    private JButton resetButton;
    public Window(){
        try {
            this.setSize(400,300);
            this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            resetButton = new JButton();
            resetButton.setSize(100,50);
            resetButton.setText("Restart");
            resetButton.addActionListener(this);
            app=new App();
            TelBot bot = new TelBot(app.getDefaultChoice1(),app.getChoice2(),app.getDefaultChoice3());
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);

            this.add(resetButton);
            this.add(app);
        }catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void opened(){
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton){
            this.remove(app);
            app=new App();
            this.add(app);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
}
