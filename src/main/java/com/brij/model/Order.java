package com.brij.model;

public class Order {
    private int id;
    private int user;
    private int product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public int getProduct() {
        return product;
    }

    public Order(int user, int product) {
        this.product = product;
        this.user = user;
    }


}