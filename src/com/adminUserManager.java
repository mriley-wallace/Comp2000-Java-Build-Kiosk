package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class adminUserManager {
    public static String companyName = "Michael's Automated Checkout";
    public static String companyDetails = "Store 001 " + "\n" + "Contact Number: 07557767300";
    public String filePath = "resources\\adminUser.txt";
    public String separator = "\\|";

    public ArrayList<adminUser> getUsers(){
        return admin;
    }

    private ArrayList<adminUser> admin = new ArrayList<adminUser>();

    public void adminLoad(){

        try{
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String dataRow = scanner.nextLine();

                String[] adminData = dataRow.split(separator);

                adminUser newAdmin = new adminUser();

                newAdmin.setUsername(adminData[0]);

                String password = adminData[1];
                newAdmin.setPassword(password);

                admin.add(newAdmin);

            }
            scanner.close();

            System.out.println("Admin file successfully loaded");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void adminSave(){
        try{
            FileWriter writer = new FileWriter(filePath);

            for (int index = 0; index < admin.size(); index++){
                String dataRow = "";
                if(index > 0){
                    dataRow += "\n";
                }
                dataRow += admin.get(index).getUsername();
                String password = admin.get(index).getPassword();
                dataRow += "|" + password;

                writer.write(dataRow);

            }

            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
