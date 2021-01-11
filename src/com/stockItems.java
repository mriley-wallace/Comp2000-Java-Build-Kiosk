package com;

public class stockItems {
    private String name;
    private float price;
    private int amount;
    private long barcode;
    private int plu;
    private int active;


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

    public long getBarcode() {
        return barcode; };

    public void setBarcode(long barcode){
        this.barcode = barcode;
    }

    public int getPlu(){
        return plu;
    }

    public void setPlu(int plu){
        this.plu = plu;
    }

    public int getActive(){ return active;}

    public void setActive(int active){this.active = active;}
}
