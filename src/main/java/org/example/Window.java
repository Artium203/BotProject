package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    private App app;
    private JButton resetButton;
    public Window(){
        this.setSize(400,300);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        resetButton = new JButton();
        resetButton.setSize(100,50);
        resetButton.setText("Restart");
        resetButton.addActionListener(this);
        app=new App();

        this.add(resetButton);
        this.add(app);
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
