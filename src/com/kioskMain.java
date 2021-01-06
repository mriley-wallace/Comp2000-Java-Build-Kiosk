package com;

import java.io.File;





public class kioskMain {


    public static void main(String[] args) {
        File file = new File("resources\\database.txt");

        if (file.exists()) {
            System.out.println("Name: " + file.getName());
            System.out.println("Hard drive location :" + file.getAbsolutePath());
            System.out.println("Can read file: " + file.canRead());
            System.out.println("Can write file " + file.canWrite());
            System.out.println("File size (bytes): " + file.length());

        } else {
            System.out.println("The file does not exist");

        }
    }

}
