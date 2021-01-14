package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class kioskMainUI extends JFrame {
    private JPanel scannedItem;
    private JPanel logoPanel;
    private JPanel containerUI;
    private JPanel activeTotal;
    private JButton cardBtn;
    private JButton cashBtn;
    private JButton subTotal;
    private JButton databaseCheck;
    private JPanel notUsed;
    private JTextArea shoppingList;
    private JPanel mainUI;
    private JButton scanItem;
    private JTextField txtItemScan;
    private JButton btnAddStock;
    private JLabel lblActiveTotalPrint;
    private JButton btnEnterAmount;
    private JButton btnExactAmount;
    private JButton btnClosestNote;
    private JTextField txtEnteredAmount;
    private JLabel lblChange;
    private JLabel lblGivenChange;
    private JTextArea receiptPanel;
    private JLabel lblCurrency;


    //Initiating the stock arraylist//
    private ArrayList<stockItems> newTransaction = new ArrayList<>();

    public void setArrayStock(ArrayList<stockItems> newTransaction) {
        this.newTransaction = newTransaction;
    }


    public static float runningTotal = 0.00f;
    public static int moreThanOneItem = 0;
    public static String addShop;
    public static float closestChange = 0.00f;


    public kioskMainUI() {
        lblCurrency.setVisible(false);

        //Loading what is in the database text file into the local arraylist//
        stockItemsManager findItem = new stockItemsManager();
        findItem.stockLoad();
        setArrayStock(findItem.getStock());

        cashBtn.setVisible(false);
        cardBtn.setVisible(false);

        // Setting up the window with my parent JPanel, initiate its close parameters to close the application, and set the size//
        setContentPane(mainUI);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CashPaymentReset();
        txtEnteredAmount.setVisible(false);
        setPreferredSize(new Dimension(800, 800));
        pack();
        setLocationRelativeTo(null);

        cashBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashPaymentActive();
                txtEnteredAmount.setVisible(true);
                scanItem.setVisible(false);
                txtItemScan.setVisible(false);
                btnAddStock.setVisible(false);

                // A simple setup for the next 3 buttons upon clicking cash. The 3 buttons will always give you: Enter an amount || The exact cost of the transaction ||
                // and finally a number that is the closest decipherable note or note combinations (5 times table)//
                String runningToString = String.format("%.02f", runningTotal);
                btnExactAmount.setText("£" + runningToString);

                while (closestChange < runningTotal) {
                    closestChange += 5.00f;
                }
                String closestToString = String.format("%.02f", closestChange);
                btnClosestNote.setText("£" + closestToString);

                cashBtn.setVisible(false);
                cardBtn.setVisible(false);
            }

        });

        subTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                endPayment();

            }
        });
        scanItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblChange.setText("");
                lblGivenChange.setText("");
                lblCurrency.setVisible(false);
                cashBtn.setVisible(false);
                cardBtn.setVisible(false);
                txtItemScan.setEnabled(true);
                btnAddStock.setEnabled(true);
                txtItemScan.setVisible(true);
                btnAddStock.setVisible(true);
                CashPaymentReset();
                txtEnteredAmount.setVisible(false);
                receiptPanel.setText("");
            }
        });
        btnAddStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockItems addedItem = new stockItems();
                try {
                    // In this for loop we are checking the temporary arraylist for an item match from the numbers entered in the scanned item box//
                    for (int currentIndex = 0; currentIndex < newTransaction.size(); currentIndex++) {

                        //A break and warning if you enter nothing. Little friendly nudge in the teeth//
                        if (txtItemScan.getText().equals("")) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "You did not enter a product.",
                                    "No Item Found",
                                    JOptionPane.WARNING_MESSAGE);
                            break;
                        } else {
                            addedItem.setBarcode(Long.parseLong(txtItemScan.getText()));
                        }

                        // if clause if we find a match//
                        if (addedItem.getBarcode() == newTransaction.get(currentIndex).getBarcode()
                                || addedItem.getBarcode() == newTransaction.get(currentIndex).getPlu()) {

                            shoppingList.setText("");

                            //Actively updating the stock quantity of the item, you will NOT be able to scan more items than there is stock for//
                            if (newTransaction.get(currentIndex).getAmount() > 0) {
                                newTransaction.get(currentIndex).setAmount(newTransaction.get(currentIndex).getAmount() - 1);
                            } else {
                                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                        "We do not have any more stock",
                                        "Not Enough Items",
                                        JOptionPane.WARNING_MESSAGE);
                                newTransaction.get(currentIndex).setActive(newTransaction.get(currentIndex).getActive() - 1);
                                runningTotal = runningTotal - newTransaction.get(currentIndex).getPrice();
                            }
                            //Setting the active number to +1 (which is only used temporarily to store multiple of the same item we are scanning//
                            int tempMore = newTransaction.get(currentIndex).getActive();
                            newTransaction.get(currentIndex).setActive(tempMore + 1);


                            /*This is the logic behind the Running Total displayed in the bottom left corner*/
                            float addTotal = newTransaction.get(currentIndex).getPrice();
                            runningTotal = runningTotal + addTotal;
                            String priceToString = String.format("%.02f", runningTotal);
                            lblActiveTotalPrint.setText("£" + priceToString);
                            /*----------------------------------------------------------------------------------*/


                            // Going through the arrays in the arraylist and only selecting the array if its active number is above 0. That means it has been used
                            // in the shopping cart so it can put it back into the shopping cart JTextArea//
                            for (com.stockItems stockItems : newTransaction) {

                                if (stockItems.getActive() > 0) {
                                    String newPriceString;
                                    newPriceString = String.format("%.02f", stockItems.getPrice() * stockItems.getActive());
                                    addShop = stockItems.getActive() + " " + stockItems.getName() +
                                            "..........£" + stockItems.getPrice() + "each" + "\n" + "\t" + "\t" + "£" + newPriceString + "\n ";
                                    shoppingList.append(addShop);
                                }
                            }
                        } else {
                            System.out.println("Finish Cycle");
                        }

                    }

                } catch (Exception f) {
                    f.printStackTrace();
                }

                txtItemScan.setText("");

            }
        });
        databaseCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                adminSplash admin = new adminSplash();
                admin.setVisible(true);
            }
        });
        cardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scanItem.setVisible(false);
                SwingWorkerLoaderCard();
                beginAnew();
                resetActiveEnd();
                findItem.itemSave();

            }
        });
        btnEnterAmount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtEnteredAmount.getText().equals("")) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "You did not enter an amount",
                            "No Amount Specified",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    //Ensuring the amount entered is received as a float so as not to interrupt the change process//
                    float enterAmount;
                    enterAmount = Float.parseFloat(String.valueOf(txtEnteredAmount.getText()));
                    String priceToString = String.format("%.02f", enterAmount);
                    ChangeToGive(Float.parseFloat(priceToString));
                    beginAnew();
                }
                findItem.itemSave();
                SwingWorkerLoaderCash();
                resetActiveEnd();
            }
        });
        btnExactAmount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeToGive(runningTotal);
                beginAnew();
                findItem.itemSave();
                SwingWorkerLoaderCash();
                resetActiveEnd();
            }
        });
        btnClosestNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeToGive(closestChange);
                beginAnew();
                findItem.itemSave();
                SwingWorkerLoaderCash();
                resetActiveEnd();
            }
        });
    }



    public void CashPaymentReset() {
        btnClosestNote.setVisible(false);
        btnEnterAmount.setVisible(false);
        btnExactAmount.setVisible(false);
    }

    public void CashPaymentActive() {
        btnClosestNote.setVisible(true);
        btnEnterAmount.setVisible(true);
        btnExactAmount.setVisible(true);
    }

    public void ChangeToGive(float button) {
        lblChange.setText("Change:");
        lblCurrency.setVisible(true);
        lblGivenChange.setText(String.format("%.02f", button - runningTotal));
    }

    public void endPayment() {
        cashBtn.setVisible(true);
        cardBtn.setVisible(true);
        txtItemScan.setEnabled(false);
        btnAddStock.setEnabled(false);
        txtEnteredAmount.setVisible(false);
        CashPaymentReset();
        scanItem.setVisible(true);
        txtItemScan.setVisible(false);
        btnAddStock.setVisible(false);

    }

    public void beginAnew() {
        endPayment();
        cashBtn.setVisible(false);
        cardBtn.setVisible(false);
        lblActiveTotalPrint.setText("£0.00");

        //Resets all the active to 0 ready for a new customer without having to close the application//
        for (com.stockItems stockItems : newTransaction) {
            stockItems.setActive(0);
        }
    }
    void SwingWorkerLoaderCash() {


        new SwingWorker<Void, Object>() {
            String receiptString;
            final float cashTotal = Float.parseFloat(lblGivenChange.getText()) + runningTotal;
            final String cashTotalToString = String.format("%.02f", cashTotal);
            final String runningTotalFormat = String.format("%.02f", runningTotal);
            @Override
            protected Void doInBackground() throws Exception {
                System.out.println("Swing Worker Thread:" + Thread.currentThread().getName());
                receiptPanel.setText("Printing Receipt");

                for (int i = 0; i < 999999999; i++) {
                    new Date();
                }
                //Taking the company name and details from the admin manager class, plus the current shopping cart text, plus the running total, cash paid and given change//
                        receiptString = adminUserManager.companyName + "\n" + "\n" + adminUserManager.companyDetails + "\n" + "------------------" + "\n" + "\n"
                                + shoppingList.getText() + "\n" + "\n" + "\n" + "\n" + "------------------" + "\n" + "Total Today: £" + runningTotalFormat + "\n"
                                + "\n" + "Cash Payment of: £" + cashTotalToString + "\n" + "\n" + "Change given: £" + lblGivenChange.getText();
                receiptPanel.setText(receiptString);

                shoppingList.setText("");
                runningTotal = 0.00f;
                return null;
            }
        }.execute();

    }

    void SwingWorkerLoaderCard() {


        new SwingWorker<Void, Object>() {
            String receiptString;
            final String runningTotalFormat = String.format("%.02f", runningTotal);
            @Override
            protected Void doInBackground() throws Exception {
                System.out.println("Swing Worker Thread:" + Thread.currentThread().getName());
                receiptPanel.setText("Printing Receipt");

                for (int i = 0; i < 999999999; i++) {
                    new Date();
                }
                //Taking the company name and details from the admin manager class, plus the current shopping cart text, plus the running total, and verified by card for the total amount//
                    receiptString = adminUserManager.companyName + "\n" + "\n" + adminUserManager.companyDetails + "\n" + "------------------" + "\n" + "\n" + shoppingList.getText()
                            + "\n" + "\n" + "\n" + "\n" + "------------------" + "\n" + "Total Today: £" + runningTotalFormat + "\n" + "\n" + "\n" + "Verified by card for amount: £" + runningTotalFormat;

                receiptPanel.setText(receiptString);

                shoppingList.setText("");
                runningTotal = 0.00f;
                return null;
            }
        }.execute();

    }

    public void resetActiveEnd(){
        for (com.stockItems stockItems : newTransaction) {
            stockItems.setActive(0);
        }
    }

    public static void main(String[] args) {
        kioskMainUI Page = new kioskMainUI();
        Page.setVisible(true);
    }
}



