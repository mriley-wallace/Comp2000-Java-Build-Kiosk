package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class kioskSplash extends JFrame {
    private JPanel mainPanel;
    private JLabel lblBackground;


    public kioskSplash() {
        ImageIcon icon = new ImageIcon("resources\\kioskSplashBackground.png");
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);

        // Setting up the window with my parent JPanel, initiate its close parameters to close the application, and set the size//
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 820));
        pack();
        setLocationRelativeTo(null);


        // Setting a listener to check that if a user presses a mouse at all to simulate a "touch here to begin" splash on a normal self-checkout//
        lblBackground.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
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
