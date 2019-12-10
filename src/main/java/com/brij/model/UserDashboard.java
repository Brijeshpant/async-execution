package com.brij.model;

import java.util.List;
import java.util.Set;

public class UserDashboard {
    private User user;

    private List<Order> userOrders;
    private Set<Product> allProducts;

    public List<Order> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<Order> userOrders) {
        this.userOrders = userOrders;
    }

    public Set<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(Set<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}