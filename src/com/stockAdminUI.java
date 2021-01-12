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
    private JComboBox comboBox1;
    private JTable tableDatabaseLoad;
    private JPanel mainPanel;
    private JTable tableLoadingDB;
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
                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
                String col[] = {"Username","Password"};

                DefaultTableModel tableModel = new DefaultTableModel(col, 0);

                tableLoadingDB.setModel(tableModel);




                for(int i = 0; i < admin.size(); i++){

                    Object[] nameToArray = {admin.get(i).getUsername(), admin.get(i).getPassword()};
                    tableModel.addRow(nameToArray);
                }


            }
        });


        btnStockDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*txtLoadDB.setText("");*/
                btnAdd.setVisible(true);
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
                String col[] = {"Stock Name","Stock Amount","Stock Price", "Stock Barcode", "Stock PLU"};

                DefaultTableModel tableModel = new DefaultTableModel(col, 0);

                tableLoadingDB.setModel(tableModel);




                for(int i = 0; i < newTransaction.size(); i++){

                    Object[] nameToArray = {newTransaction.get(i).getName(), newTransaction.get(i).getAmount(),
                            newTransaction.get(i).getPrice(), newTransaction.get(i).getBarcode(), newTransaction.get(i).getPlu()};
                    tableModel.addRow(nameToArray);


                }
            }
        });
    }

    public static void main(String[] args) {
        stockAdminUI Page = new stockAdminUI();
        Page.setVisible(true);
    }
}
