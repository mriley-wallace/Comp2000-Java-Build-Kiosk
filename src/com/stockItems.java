package com;

public class stockItems {
    private String name;
    private float price;
    private int amount;
    private String barcode;
    private String plu;


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

    public String getBarcode() {
        return barcode; };

    public void setBarcode(String barcode){
        this.barcode = barcode;
    }

    public String getPlu(){
        return plu;
    }

    public void setPlu(String plu){
        this.plu = plu;
    }
}
