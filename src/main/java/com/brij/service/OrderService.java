package com.brij.service;

import com.brij.model.Order;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
    Order addOrder(int id, int id1);

    List<Order> getOrder(int userId);

    HashMap<Integer, Order> getOrders();

}