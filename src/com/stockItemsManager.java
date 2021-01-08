package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class stockItemsManager {

    public String filePath = "resources\\database.txt";
    public String separator = "\\|";

    public ArrayList<stockItems> getStock(){
        return stock;
    }

    private final ArrayList<stockItems> stock = new ArrayList<stockItems>();

    public void stockLoad(){

        try{
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String dataRow = scanner.nextLine();

                String[] stockData = dataRow.split(separator);

                stockItems newStock = new stockItems();
                newStock.setName(stockData[0]);

                float priceToFloat = Float.parseFloat(stockData[1]);
                newStock.setPrice(priceToFloat);

                int amountToInt = Integer.parseInt(stockData[2]);
                newStock.setAmount(amountToInt);

                double barcodeToDouble = Double.parseDouble(stockData[3]);
                newStock.setBarcode(barcodeToDouble);

                int pluToInt = Integer.parseInt(stockData[4]);
                newStock.setPlu(pluToInt);


                stock.add(newStock);

            }
            scanner.close();

            System.out.println("Admin file successfully loaded");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}