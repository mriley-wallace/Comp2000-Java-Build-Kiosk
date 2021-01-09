package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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
    private JPanel mainUI;
    private JButton scanItem;
    private JTextField txtItemScan;
    private JButton btnAddStock;
    private JLabel lblActiveTotalPrint;

    private ArrayList<stockItems> newTransaction = new ArrayList<>();

    public void setArrayStock(ArrayList<stockItems> newTransaction){
        this.newTransaction = newTransaction;
    }

    public float runningTotal = 0.00f;
    public int moreThanOneItem = 0;
    public String showShop;
    public int currentIndexMultiple;

    public kioskMainUI() {
        stockItemsManager findItem = new stockItemsManager();
        findItem.stockLoad();
        setArrayStock(findItem.getStock());
        boolean[] multipleItem = new boolean[newTransaction.size()];
        Arrays.fill(multipleItem, Boolean.FALSE);
        cashBtn.setEnabled(false);
        cardBtn.setEnabled(false);
        setContentPane(mainUI);
        setLocationRelativeTo(null);
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


                stockItems addedItem = new stockItems();
                addedItem.setBarcode(Double.parseDouble(txtItemScan.getText()));

                try {
                    for (int currentIndex = 0; currentIndex < newTransaction.size(); currentIndex++) {

                        if (addedItem.getBarcode() == newTransaction.get(currentIndex).getBarcode()
                                || addedItem.getBarcode() == newTransaction.get(currentIndex).getPlu()) {
                            if(multipleItem[currentIndex] == false){
                                multipleItem[currentIndex] = true;
                                moreThanOneItem = 1;
                                float addTotal = newTransaction.get(currentIndex).getPrice();
                                runningTotal = runningTotal + addTotal;
                                String priceToString = String.format("%.02f", runningTotal);
                                lblActiveTotalPrint.setText("£" + priceToString);
                                showShop = moreThanOneItem + " " + newTransaction.get(currentIndex).getName() +
                                        "......." + " £" + newTransaction.get(currentIndex).getPrice() + "\n";
                                shoppingList.append(showShop);

                            }else {
                                moreThanOneItem += 1;
                                shoppingList.replaceRange(String.valueOf(moreThanOneItem),0,1);
                            }

                        } else {
                            System.out.println("No Stock Item Found");
                        }

                    }

                } catch (Exception f){
                    f.printStackTrace();
                }
                txtItemScan.setText("");

            }
        });
        databaseCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public static void main(String[] args) {
        kioskMainUI Page = new kioskMainUI();
        Page.setVisible(true);

    }
}
