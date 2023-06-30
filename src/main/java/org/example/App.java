package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class App extends JPanel implements ActionListener {
    JCheckBox numbersAPI;
    JCheckBox catFactsAPI;
    JCheckBox jokesAPI;
    JCheckBox quotesAPI;
    JCheckBox countryAPI;
    List<JCheckBox> checkBoxesList;
    JButton selectedButton = new JButton("Set options");
    JLabel selectedOpLabel = new JLabel("Pick 3 from 5 to display in the bot");
    JLabel selectedOpError = new JLabel("Only 3 can be selected");
    HashMap<String, Integer> savedSelections;
    public App(){
        this.numbersAPI = new JCheckBox("Numbers");
        numbersAPI.setBounds(10,200,100,50);
        numbersAPI.setSelected(true);

        this.catFactsAPI = new JCheckBox("Cat-Facts");
        catFactsAPI.setBounds(110,200,100,50);
        catFactsAPI.setSelected(true);

        this.jokesAPI = new JCheckBox("Jokes");
        jokesAPI.setBounds(210,200,100,50);
        jokesAPI.setSelected(true);

        this.quotesAPI = new JCheckBox("Quotes");
        quotesAPI.setBounds(310,200,100,50);

        this.countryAPI = new JCheckBox("Countries");
        countryAPI.setBounds(410,200,150,50);

        this.add(selectedOpLabel);
        selectedOpLabel.setBounds(10,150,300,50);
        this.add(selectedButton);

        selectedButton.setBounds(10,300,150,50);
        selectedButton.addActionListener(this);
        this.add(selectedOpError);

        selectedOpError.setBackground(Color.red);
        selectedOpError.setBounds(10,350,400,50);
        selectedOpError.setVisible(false);

        this.add(numbersAPI);
        this.add(catFactsAPI);
        this.add(jokesAPI);
        this.add(quotesAPI);
        this.add(countryAPI);

        this.checkBoxesList = List.of(numbersAPI, catFactsAPI, jokesAPI, quotesAPI, countryAPI);
        this.savedSelections = new HashMap<>();
        this.savedSelections.put("Numbers", 1);
        this.savedSelections.put("Jokes", 1);
        this.savedSelections.put("Cat-Facts", 1);
        this.savedSelections.put("Countries", 0);
        this.savedSelections.put("Quotes", 0);
        this.setLayout(null);
        this.setSize(800,600);
        this.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        int counter = 0;
        if (e.getSource() == selectedButton){
            for (JCheckBox checkBox : checkBoxesList){
                if (checkBox.isSelected()){
                    counter++;
                }
            }
        }
        if (counter != 3){
            selectedOpError.setVisible(true);
        }
        else {
            selectedOpError.setVisible(false);
            for (JCheckBox checkBox: checkBoxesList){
                if (checkBox.isSelected()){
                    savedSelections.put(checkBox.getText(), 1);
                }
                else {
                    savedSelections.put(checkBox.getText(), 0);
                }
            }
        }
    }
}