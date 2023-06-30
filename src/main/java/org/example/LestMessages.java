package org.example;

import javax.swing.*;

public class LestMessages extends JPanel {
    JLabel message = new JLabel("<html>Last ten messages: </html>");// html helps with text

    public LestMessages(){
        message.setBounds(10,70, 800,600);
        this.add(message);

        this.setLayout(null);
        this.setSize(800,600);
        this.setVisible(false);
    }
}
