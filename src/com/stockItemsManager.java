package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class stockItemsManager {

    // selecting where in the project to find the text files//
    public String filePath = "resources\\database.txt";
    public String separator = "\\|";

    public ArrayList<stockItems> getStock(){
        return stock;
    }

    private ArrayList<stockItems> stock = new ArrayList<stockItems>();

    // Method to read data to the text file in the format required, and then remove the "|" from the text file so it is in a more readable format //
    public void stockLoad(){

        try{
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String dataRow = scanner.nextLine();

                String[] stockData = dataRow.split(separator);

                stockItems newStock = new stockItems();

                newStock.setName(stockData[0]);


                int amountToInt = Integer.parseInt(stockData[1]);
                newStock.setAmount(amountToInt);

                float priceToFloat = Float.parseFloat(stockData[2]);
                newStock.setPrice(priceToFloat);

                long barcodeToLong = Long.parseLong(stockData[3]);
                newStock.setBarcode(barcodeToLong);

                int pluToLong = Integer.parseInt(stockData[4]);
                newStock.setPlu(pluToLong);

                int activeShopToInt = Integer.parseInt(stockData[5]);
                newStock.setActive((activeShopToInt));


                stock.add(newStock);

            }
            scanner.close();

            System.out.println("Stock file successfully loaded");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    //Method to save to the text file, reverting the data to include the "|" separator//
    public void itemSave(){
        try{
            FileWriter writer = new FileWriter(filePath);

            for(int index = 0; index < stock.size(); index++){
                String dataRow = "";
                if(index > 0){
                    dataRow += "\n";
                }
                dataRow += stock.get(index).getName();
                float priceToFloat = stock.get(index).getPrice();
                int amountToInt = stock.get(index).getAmount();
                long barcodeToLong = stock.get(index).getBarcode();
                int pluToInt = stock.get(index).getPlu();
                int activeShopToInt = stock.get(index).getActive();
                dataRow += "|" + amountToInt + "|" + priceToFloat + "|" + barcodeToLong + "|" + pluToInt + "|" + activeShopToInt;

                writer.write(dataRow);
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}