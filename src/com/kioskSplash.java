package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class kioskSplash extends JFrame {
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


    public kioskSplash(){
    setContentPane(mainPanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setPreferredSize(new Dimension(500, 500));

    pack();
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
                    for (int index = 0; index < admin.size(); index++) {

                        if (tempAdmin.getUsername().equals(admin.get(index).getUsername())
                                && tempAdmin.getPassword().equals(admin.get(index).getPassword())) {

                            setVisible(false);
                            kioskMainUI Page = new kioskMainUI();
                            Page.setVisible(true);
                            break;
                        } else {
                            System.out.println("No Admin User Found // Credentials Incorrect");
                        }
                    }
                } catch (Exception f){
                    f.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        kioskSplash Page = new kioskSplash();
        Page.setVisible(true);
    }
}

