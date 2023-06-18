package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class App extends JPanel {
    private BotTel bot;
    private JComboBox choicesOfAPI1;
    private JComboBox choicesOfAPI2;
    private JComboBox choicesOfAPI3;
    private static String defaultChoice1 = "Jokes";
    private static String defaultChoice2 = "Cat-Facts";
    private static String defaultChoice3 = "na";
    private final List<String> API = Arrays.asList("Jokes","Cat-Facts","na","ba","ha");
    private static int counter =0;
    public App(){
        this.add(selectAPI());
    }

    private JPanel selectAPI() {
        String[] arr = new String[API.size()];
        for (int i = 0; i < API.size(); i++) {
            arr[i] = API.get(i);
        }
        JPanel panel = new JPanel();
        choicesOfAPI1 = new JComboBox(arr);
        choicesOfAPI2 = new JComboBox(arr);
        choicesOfAPI3 = new JComboBox(arr);
        choicesOfAPI1.addActionListener(e ->{
            if (choicesOfAPI1.getSelectedItem() == choicesOfAPI2.getSelectedItem() && choicesOfAPI1.getSelectedItem() != choicesOfAPI1.getItemAt(0) && choicesOfAPI2.getSelectedItem() != choicesOfAPI2.getItemAt(0)){
                choicesOfAPI2.removeItem(choicesOfAPI1.getSelectedItem());
                choicesOfAPI1.removeItem(choicesOfAPI2.getSelectedItem());
            }
            if (choicesOfAPI1.getSelectedItem() == choicesOfAPI3.getSelectedItem() && choicesOfAPI1.getSelectedItem() != choicesOfAPI1.getItemAt(0) && choicesOfAPI3.getSelectedItem() != choicesOfAPI3.getItemAt(0)){
                choicesOfAPI3.removeItem(choicesOfAPI1.getSelectedItem());
                choicesOfAPI1.removeItem(choicesOfAPI3.getSelectedItem());
            }
            this.defaultChoice1 = choicesOfAPI1.getSelectedItem().toString();
        });
        choicesOfAPI2.addActionListener(e ->{
            if (choicesOfAPI1.getSelectedItem() == choicesOfAPI2.getSelectedItem() && choicesOfAPI1.getSelectedItem() != choicesOfAPI1.getItemAt(0) && choicesOfAPI2.getSelectedItem() != choicesOfAPI2.getItemAt(0)){
                choicesOfAPI1.removeItem(choicesOfAPI2.getSelectedItem());
                choicesOfAPI2.removeItem(choicesOfAPI1.getSelectedItem());
            }
            if (choicesOfAPI2.getSelectedItem() == choicesOfAPI3.getSelectedItem() && choicesOfAPI3.getSelectedItem() != choicesOfAPI3.getItemAt(0) && choicesOfAPI2.getSelectedItem() != choicesOfAPI2.getItemAt(0)){
                choicesOfAPI3.removeItem(choicesOfAPI1.getSelectedItem());
                choicesOfAPI1.removeItem(choicesOfAPI3.getSelectedItem());
            }
            defaultChoice2 = choicesOfAPI2.getSelectedItem().toString();
//            bot.setTextNo(defaultChoice2);
        });
        choicesOfAPI3.addActionListener(e ->{
            if (choicesOfAPI3.getSelectedItem() == choicesOfAPI2.getSelectedItem() && choicesOfAPI3.getSelectedItem() != choicesOfAPI3.getItemAt(0) && choicesOfAPI2.getSelectedItem() != choicesOfAPI2.getItemAt(0)){
                choicesOfAPI2.removeItem(choicesOfAPI3.getSelectedItem());
                choicesOfAPI3.removeItem(choicesOfAPI2.getSelectedItem());
            }
            if (choicesOfAPI1.getSelectedItem() == choicesOfAPI3.getSelectedItem() && choicesOfAPI1.getSelectedItem() != choicesOfAPI1.getItemAt(0) && choicesOfAPI3.getSelectedItem() != choicesOfAPI3.getItemAt(0)){
                choicesOfAPI1.removeItem(choicesOfAPI3.getSelectedItem());
                choicesOfAPI3.removeItem(choicesOfAPI1.getSelectedItem());
            }
            defaultChoice3 = choicesOfAPI3.getSelectedItem().toString();
        });
        if (choicesOfAPI1.getSelectedItem() == choicesOfAPI2.getSelectedItem()){
            choicesOfAPI2.removeItem(choicesOfAPI1.getSelectedItem());
            choicesOfAPI1.removeItem(choicesOfAPI2.getSelectedItem());
        }
        if (choicesOfAPI1.getSelectedItem() == choicesOfAPI3.getSelectedItem()){
            choicesOfAPI3.removeItem(choicesOfAPI1.getSelectedItem());
        }
        if (choicesOfAPI2.getSelectedItem()==choicesOfAPI3.getSelectedItem()){
            choicesOfAPI3.removeItem(choicesOfAPI2.getSelectedItem());
            choicesOfAPI2.removeItem(choicesOfAPI3.getSelectedItem());
        }

        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.add(choicesOfAPI1);
        panel.add(choicesOfAPI2);
        panel.add(choicesOfAPI3);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Select 3 types"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return panel;
    }
    public JPanel showGraph(){
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Activtiy"),BorderFactory.createEmptyBorder(5,5,5,5)));
        return panel;
    }

    public String getDefaultChoice1() {
        return defaultChoice1;
    }

    public String getChoice2() {
        return defaultChoice2;
    }

    public String getDefaultChoice3() {
        return defaultChoice3;
    }
}
