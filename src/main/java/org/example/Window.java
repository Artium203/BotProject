package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    App controlPanel;
    InfoCount botInfo;
    LestMessages lestMessages;
    private static JButton controlButton = new JButton("Bot settings");
    private static JButton statisticButton = new JButton("Statistics");
    private static JButton historyButton = new JButton("Latest requests");
    public Window(){
        controlButton.setBounds(10,10,150,50);
        controlButton.addActionListener(this);
        this.add(controlButton);

        statisticButton.setBounds(controlButton.getX()+ controlButton.getWidth(),10,150,50);
        statisticButton.addActionListener(this);
        this.add(statisticButton);

        historyButton.setBounds(statisticButton.getX()+ statisticButton.getWidth(),10,150,50);
        historyButton.addActionListener(this);
        this.add(historyButton);

        this.controlPanel = new App();
        this.add(controlPanel);

        lestMessages = new LestMessages();
        this.add(lestMessages);

        this.botInfo = new InfoCount();
        this.add(botInfo);

        this.setLayout(null);
        this.setSize(800,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controlPanel.setVisible(e.getSource() == controlButton);
        botInfo.setVisible(e.getSource() == statisticButton);
        lestMessages.setVisible(e.getSource() == historyButton);
    }
}