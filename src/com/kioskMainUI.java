package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
    private JTextField txtItemScan;
    private JButton btnAddStock;

    private ArrayList<stockItems> newTransaction = new ArrayList<>();

    public void setArrayStock(ArrayList<stockItems> newTransaction){
        this.newTransaction = newTransaction;
    }

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
                txtItemScan.setEnabled(false);
            }
        });
        scanItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashBtn.setEnabled(false);
                cardBtn.setEnabled(false);
                txtItemScan.setEnabled(true);
                btnAddStock.setEnabled(true);
            }
        });
        btnAddStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockItemsManager findItem = new stockItemsManager();
                findItem.stockLoad();
                setArrayStock(findItem.getStock());

                String tempNumber = txtItemScan.toString();


                try {
                    for (int index = 0; index < newTransaction.size(); index++) {

                        if (tempNumber == (newTransaction.get(index).getBarcode()) || tempNumber == (newTransaction.get(index).getPlu())) {

                        shoppingList.append(newTransaction.get(index).getName());

                            break;

                        } else {
                            System.out.println("No Stock Item Found");
                        }
                    }
                } catch (Exception f){
                    f.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) {
        kioskMainUI Page = new kioskMainUI();
        Page.setVisible(true);

    }
}
