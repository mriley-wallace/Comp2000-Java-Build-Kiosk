package com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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

        String[] empty = new String[4];
        Arrays.fill(empty, "");
        btnAdd.setVisible(false);
        btnEdit.setVisible(false);
        btnDelete.setVisible(false);

        stockItemsManager findItem = new stockItemsManager();
        findItem.stockLoad();
        setArrayStock(findItem.getStock());

        adminUserManager staffLogin = new adminUserManager();
        staffLogin.adminLoad();
        setArrayAdmin(staffLogin.getUsers());

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));
        pack();
        setLocationRelativeTo(null);
        btnAdminDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox1.removeAllItems();
                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
                btnOrderStock.setEnabled(false);
                comboBox1.setEnabled(false);
                String[] col = {"Username", "Password"};
                DefaultTableModel adminModel = new DefaultTableModel(col, 0);

                tableLoadingDB.setModel(adminModel);
                adminModel.getDataVector().removeAllElements();
                adminModel.addRow(col);


                for (com.adminUser adminUser : admin) {

                    Object[] nameToArray = {adminUser.getUsername(), adminUser.getPassword()};
                    adminModel.addRow(nameToArray);
                }

                btnAdd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] emptyRow = {""};
                        adminModel.addRow(emptyRow);
                        admin.add(new adminUser());
                    }
                });

                btnEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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
                        if(tableLoadingDB.getSelectedRow() < 0){
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "You did not select an admin for deletion.",
                                    "No Admin Selected.",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
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


                for (com.stockItems stockItems : newTransaction) {

                    Object[] nameToArray = {stockItems.getName(), stockItems.getAmount(),
                            stockItems.getPrice(), stockItems.getBarcode(), stockItems.getPlu()};
                    stockModel.addRow(nameToArray);
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

                        if (!comboBox1.isEnabled()) {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "There is no item currently low on stock. No order made.",
                                    "Stock Levels OK",
                                    JOptionPane.WARNING_MESSAGE);

                        } else {
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
                        if(tableLoadingDB.getSelectedRow() < 0){
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "You did not select an admin for deletion.",
                                    "No Admin Selected.",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
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

