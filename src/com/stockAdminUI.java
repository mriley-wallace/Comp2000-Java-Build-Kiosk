package com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class stockAdminUI extends JFrame{
    private JButton btnAdd;
    private JButton btnAdminDB;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnStockDB;
    private JButton btnOrderStock;
    private JComboBox<String> comboBox1;
    private JTable tableDatabaseLoad;
    private JPanel mainPanel;
    private JTable tableLoadingDB;
    private JButton btnBackToShop;
    private JTextArea txtLoadDB;

    private ArrayList<stockItems> newTransaction = new ArrayList<>();
    private ArrayList<adminUser> admin = new ArrayList<>();

    public void setArrayStock(ArrayList<stockItems> newTransaction) {
        this.newTransaction = newTransaction;
    }

    public void setArrayAdmin(ArrayList<adminUser> admin){
        this.admin = admin;
    }

    public stockAdminUI() {

        btnAdd.setVisible(false);
        btnEdit.setVisible(false);
        btnDelete.setVisible(false);

        // Setting up the window with my parent JPanel, initiate its close parameters to close the application, and set the size//
        stockItemsManager findItem = new stockItemsManager();
        findItem.stockLoad();
        setArrayStock(findItem.getStock());
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));
        pack();
        setLocationRelativeTo(null);

        adminUserManager staffLogin = new adminUserManager();
        staffLogin.adminLoad();
        setArrayAdmin(staffLogin.getUsers());

        btnAdminDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox1.removeAllItems();
                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
                btnOrderStock.setEnabled(false);
                comboBox1.setEnabled(false);

                //Setting a tableModel to be nested inside the JTable. It is the tableModel we manipulate not the JTable//
                String[] col = {"Username", "Password"};
                DefaultTableModel adminModel = new DefaultTableModel(col, 0);
                tableLoadingDB.setModel(adminModel);
                adminModel.getDataVector().removeAllElements();
                adminModel.addRow(col);

            //loop through the arrays in the arraylist and add the arrays to an object array to add each item to the tableModel//
                for (com.adminUser adminUser : admin) {

                    Object[] nameToArray = {adminUser.getUsername(), adminUser.getPassword()};
                    adminModel.addRow(nameToArray);
                }

                btnAdd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Quickest solution to adding a row of nulls//
                        String[] emptyRow = {""};
                        adminModel.addRow(emptyRow);
                        admin.add(new adminUser());
                    }
                });

                btnEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Loop through the rows of the current tableModel starting from position 1 not 0 because position 0 is the column names//
                        //Sets the information up and then writes it back to the text file//
                        for (int i = 1; i < adminModel.getRowCount(); i++) {
                            if (adminModel.getValueAt(i, 0) == null) {
                                break;
                            } else {
                                admin.get(i-1).setUsername(String.valueOf(adminModel.getValueAt(i, 0)));
                                admin.get(i-1).setPassword(String.valueOf(adminModel.getValueAt(i, 1)));
                            }
                            staffLogin.adminSave();
                        }
                        setVisible(false);
                        stockAdminUI Page = new stockAdminUI();
                        Page.setVisible(true);
                    }
                });
                btnDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // A quick catch if you don't select anything, the index returns -1 to a < 0 equation works//
                        if(tableLoadingDB.getSelectedRow() < 0){
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "You did not select an admin for deletion.",
                                    "No Admin Selected.",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            //remembering to take 1 from the selected row because it is ahead by 1 position as position 0 is the column name//
                            //then removing it from the temporary arraylist and then saving it to the text file//
                            int deleteDB = tableLoadingDB.getSelectedRow() -1;
                            admin.remove(deleteDB);
                        }
                        staffLogin.adminSave();
                        setVisible(false);
                        stockAdminUI Page = new stockAdminUI();
                        Page.setVisible(true);
                    }
                });
            }

        });

        btnStockDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox1.removeAllItems();
                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
                btnOrderStock.setEnabled(true);
                comboBox1.setEnabled(true);
                String[] col = {"Stock Name", "Stock Amount", "Stock Price", "Stock Barcode", "Stock PLU"};
                DefaultTableModel stockModel = new DefaultTableModel(col, 0);

                tableLoadingDB.setModel(stockModel);
                stockModel.getDataVector().removeAllElements();
                stockModel.addRow(col);

                //loop through the arrays in the arraylist and add the arrays to an object array to add each item to the tableModel//
                for (com.stockItems stockItems : newTransaction) {

                    Object[] nameToArray = {stockItems.getName(), stockItems.getAmount(),
                            stockItems.getPrice(), stockItems.getBarcode(), stockItems.getPlu()};
                    stockModel.addRow(nameToArray);

                    //If the registered amount of stock is below 100, it will add it to the combo box ready for quick ordering.//
                    if (stockItems.getAmount() < 100) {
                        comboBox1.addItem(stockItems.getName());
                    }

                }

                btnAdd.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnEdit.setVisible(true);
                        String[] emptyRow = {""};
                        stockModel.addRow(emptyRow);
                        newTransaction.add(new stockItems());

                    }
                });

                btnOrderStock.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //When you try to order stock but there is no low stock, a polite message to say stock levels are ok and then disables the button//
                        if (!comboBox1.isEnabled()) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "There is no item currently low on stock. No order made.",
                                    "Stock Levels OK",
                                    JOptionPane.WARNING_MESSAGE);
                            btnOrderStock.setEnabled(false);

                        } else {

                            //Orders the selected Index of the combo box and displays that name into the message box then removes it from the combo box//
                            btnOrderStock.setEnabled(true);
                            String orderedItem = String.valueOf(comboBox1.getSelectedItem());
                            int index = comboBox1.getSelectedIndex();
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "We have ordered the item: " + orderedItem + "\n" + "Check your deliveries tomorrow.",
                                    "Items Successfully Ordered",
                                    JOptionPane.WARNING_MESSAGE);
                            comboBox1.removeItemAt(index);
                            if (comboBox1.getSelectedItem() != null) {
                                comboBox1.setSelectedIndex(0);
                            } else {
                                comboBox1.setEnabled(false);
                            }

                        }
                    }
                });
                btnEdit.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Loop through the rows of the current tableModel starting from position 1 not 0 because position 0 is the column names//
                        //Sets the information up and then writes it back to the text file//
                        for (int i = 1; i < stockModel.getRowCount(); i++) {
                            if (stockModel.getValueAt(i, 0) == null) {
                                break;
                            } else {
                                newTransaction.get(i - 1).setName(String.valueOf(stockModel.getValueAt(i, 0)));
                                newTransaction.get(i - 1).setAmount(Integer.parseInt(String.valueOf(stockModel.getValueAt(i, 1))));
                                newTransaction.get(i - 1).setPrice(Float.parseFloat(String.valueOf(stockModel.getValueAt(i, 2))));
                                newTransaction.get(i - 1).setBarcode(Long.parseLong(String.valueOf(stockModel.getValueAt(i, 3))));
                                newTransaction.get(i - 1).setPlu(Integer.parseInt(String.valueOf(stockModel.getValueAt(i, 4))));
                                newTransaction.get(i - 1).setActive(0);
                            }
                            findItem.itemSave();
                        }
                        setVisible(false);
                        stockAdminUI Page = new stockAdminUI();
                        Page.setVisible(true);
                    }
                });
                btnDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // A quick catch if you don't select anything, the index returns -1 to a < 0 equation works//
                        if(tableLoadingDB.getSelectedRow() < 0){
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "You did not select an admin for deletion.",
                                    "No Admin Selected.",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            //remembering to take 1 from the selected row because it is ahead by 1 position as position 0 is the column name//
                            //then removing it from the temporary arraylist and then saving it to the text file//
                            int deleteDB = tableLoadingDB.getSelectedRow() -1;
                            newTransaction.remove(deleteDB);
                        }
                        findItem.itemSave();
                        setVisible(false);
                        stockAdminUI Page = new stockAdminUI();
                        Page.setVisible(true);
                    }

                });
            }
        });
        btnBackToShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                kioskMainUI Page = new kioskMainUI();
                Page.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        stockAdminUI Page = new stockAdminUI();
        Page.setVisible(true);
    }
}

