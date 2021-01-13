package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class adminSplash extends JFrame {
    private JButton beginButton;
    private JPanel mainPanel;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JLabel lblUserName;
    private JLabel lblPassword;

    private ArrayList<adminUser> admin = new ArrayList<>();

    public void setArrayAdmin(ArrayList<adminUser> admin){
        this.admin = admin;
    }


    public adminSplash(){

        // Setting up the window with my parent JPanel, initiate its close parameters to close the application, and set the size//
    setContentPane(mainPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(800, 800));
        pack();
        setLocationRelativeTo(null);

    //Loading the admin text file ready for use//
    adminUserManager staffLogin = new adminUserManager();
    staffLogin.adminLoad();
    setArrayAdmin(staffLogin.getUsers());

        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Using a loop to go through the arrays in the arraylist for admin to find a match based on whats been entered//
                // A boolean catch to stop a bug of the error message pane appearing even after a successful entry//
                boolean errorCatch = false;
                adminUser tempAdmin = new adminUser();
                tempAdmin.setUsername(txtUsername.getText());
                tempAdmin.setPassword(txtPassword.getText());

                    for (com.adminUser adminUser : admin) {
                        if (tempAdmin.getUsername().equals(adminUser.getUsername())
                                && tempAdmin.getPassword().equals(adminUser.getPassword())) {
                            txtUsername.setText("");
                            txtPassword.setText("");
                            setVisible(false);
                            stockAdminUI Page = new stockAdminUI();
                            Page.setVisible(true);
                            errorCatch = false;
                            break;
                        }
                        errorCatch = true;
                    }

                if (errorCatch){
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "You did not enter the correct credentials.",
                            "No Admin Found",
                            JOptionPane.WARNING_MESSAGE);
                    System.out.println("No Admin User Found // Credentials Incorrect");
                }
            }
        });
    }

    public static void main(String[] args) {
        adminSplash Page = new adminSplash();
        Page.setVisible(true);
    }
}

