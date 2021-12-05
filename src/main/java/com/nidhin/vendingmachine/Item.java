package com.nidhin.vendingmachine;


public class Item {
    private final String id;
    private final String name;
    private final int price;
    private int quantity = 0;

    public Item(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity) throws Exception {
        if (this.quantity < quantity) {
            throw new Exception("Not enough quantity");
        }
        this.quantity -= quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }
}