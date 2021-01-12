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
    setContentPane(mainPanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    setPreferredSize(new Dimension(800, 800));

    pack();
        setLocationRelativeTo(null);
        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminUserManager staffLogin = new adminUserManager();
                staffLogin.adminLoad();
                setArrayAdmin(staffLogin.getUsers());

                adminUser tempAdmin = new adminUser();
                tempAdmin.setUsername(txtUsername.getText());
                tempAdmin.setPassword(txtPassword.getText());

                try {
                    for (com.adminUser adminUser : admin) {

                        if (tempAdmin.getUsername().equals(adminUser.getUsername())
                                && tempAdmin.getPassword().equals(adminUser.getPassword())) {

                            setVisible(false);
                            stockAdminUI Page = new stockAdminUI();
                            Page.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "You did not enter the correct credentials.",
                                    "No Admin Found",
                                    JOptionPane.WARNING_MESSAGE);
                            System.out.println("No Admin User Found // Credentials Incorrect");
                        }
                        break;
                    }
                } catch (Exception f){
                    f.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        adminSplash Page = new adminSplash();
        Page.setVisible(true);
    }
}
