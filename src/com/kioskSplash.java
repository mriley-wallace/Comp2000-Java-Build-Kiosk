package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class kioskSplash extends JFrame {
    private JButton beginButton;
    private JPanel mainPanel;

    public kioskSplash(){
    setContentPane(mainPanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setPreferredSize(new Dimension(500, 500));

    pack();
        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                kioskMainUI Page = new kioskMainUI();
                Page.setVisible(true);

            }
        });
    }

    public static void main(String[] args) {
        kioskSplash Page = new kioskSplash();
        Page.setVisible(true);
    }
}

