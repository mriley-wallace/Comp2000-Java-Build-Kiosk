package com;

public class stockItems {
    private String name;
    private float price;
    private int amount;
    private double barcode;
    private int plu;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public double getBarcode() {
        return barcode; };

    public void setBarcode(double barcode){
        this.barcode = barcode;
    }

    public int getPlu(){
        return plu;
    }

    public void setPlu(int plu){
        this.plu = plu;
    }
}
