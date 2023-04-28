package com.brij.service;

import com.brij.model.Order;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
    Order addOrder(int userId, int productId);

    List<Order> getOrder(int userId);

    HashMap<Integer, Order> getOrders();

}