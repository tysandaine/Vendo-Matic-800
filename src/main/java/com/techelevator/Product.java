package com.techelevator;

public class Product {
    private String name;
    private double price;
    private String item;
    private int counter;

    public Product(){}

    public Product(String name, double price, String item, int counter) {
        this.name = name;
        this.price = price;
        this.setItem(item);
        this.setCounter(counter);
    }

    public void decCounter(){
        counter--;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
