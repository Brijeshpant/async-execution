package com.brij.service;

import com.brij.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private HashMap<Integer, Order> userOrders = new HashMap<Integer, Order>();

    @Override
    public Order addOrder(int userId, int productId) {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Order order = new Order(userId, productId);
        userOrders.put(userId, order);
        return order;

    }

    @Override
    public List<Order> getOrder(int userId) {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final List<Order> orders = userOrders.keySet().stream().filter(u -> u == userId).map(k -> userOrders.get(k)).collect(Collectors.toList());
        if (!orders.isEmpty()) return orders;
        throw new RuntimeException("No order found");
    }

    @Override
    public HashMap<Integer, Order> getOrders() {
        return this.userOrders;
    }
}