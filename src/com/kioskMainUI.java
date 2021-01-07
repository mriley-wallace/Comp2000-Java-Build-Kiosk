package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class kioskMainUI extends JFrame {
    private JPanel scannedItem;
    private JPanel receiptPrint;
    private JPanel containerUI;
    private JPanel activeTotal;
    private JButton cardBtn;
    private JButton cashBtn;
    private JButton subTotal;
    private JButton databaseCheck;
    private JPanel notUsed;
    private JTextArea shoppingList;
    private JTextArea receiptPrintOut;
    private JTextArea activeTotalPrint;
    private JPanel mainUI;
    private JButton scanItem;

    public kioskMainUI() {
        cashBtn.setEnabled(false);
        cardBtn.setEnabled(false);
        setContentPane(mainUI);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(500, 500));

        pack();

        cashBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        subTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashBtn.setEnabled(true);
                cardBtn.setEnabled(true);
            }
        });
        scanItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashBtn.setEnabled(false);
                cardBtn.setEnabled(false);
            }
        });
    }
    public static void main(String[] args) {
        kioskMainUI Page = new kioskMainUI();
        Page.setVisible(true);

    }
}
