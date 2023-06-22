package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    private App app;
    private String a;
    private String b;
    private String c;
    private BotTel bot;
    private JButton resetButton;
    private JButton updateButton;
    public Window(){
        try {
            this.setSize(800,800);
            this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            updateButton = new JButton();
            resetButton = new JButton();
            resetButton.setSize(100,50);
            resetButton.setText("Restart");
            updateButton.setText("Update");
            updateButton.setBounds(resetButton.getX(),resetButton.getY()+resetButton.getHeight(),100,50);
            resetButton.addActionListener(this);
            app=new App();
            bot = new BotTel(app.getDefaultChoice1(),app.getChoice2(),app.getDefaultChoice3());
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            updateButton.addActionListener(e -> {
                a=app.getDefaultChoice1();
                b =app.getChoice2();
                c=app.getDefaultChoice3();
                bot.setChoice1(a);
                bot.setChoice2(b);
                bot.setChoice3(c);
                System.out.println(app.getDefaultChoice1());
            });
            api.registerBot(bot);

            this.add(updateButton);
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