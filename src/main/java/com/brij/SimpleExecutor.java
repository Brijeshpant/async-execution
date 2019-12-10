package com.brij;

import com.brij.model.Order;
import com.brij.model.Product;
import com.brij.model.User;
import com.brij.model.UserDashboard;
import com.brij.service.OrderService;
import com.brij.service.ProductService;
import com.brij.service.UserService;

import java.util.*;

public class SimpleExecutor implements MyExecutor {
    OrderService orderService;
    UserService userService;
    ProductService productService;

    public SimpleExecutor(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void execute(long milliseconds) {
        executeMe(1000);
        executeMe(1000);

    }

    @Override
    public Order createOrder(int userId, int productId) {
        Product product = productService.getProduct(productId);
        User user = userService.getUser(userId);
        if(Objects.nonNull(product) && Objects.nonNull(user)) {
            return orderService.addOrder(userId, productId);
        }
        throw new RuntimeException("Failed to create order");
    }

    @Override
    public UserDashboard getDashBoard(int userId) {
        User user = userService.getUser(userId);
        List<Order> orders;
        Set<Product> products;
        try {
            orders = orderService.getOrder(userId);
        } catch (Exception e) {
            orders = new ArrayList<>();
        }
        try {
            products = productService.getProducts();
        } catch (Exception e) {
            products = new HashSet<>();
        }

        UserDashboard userDashboard = new UserDashboard();
        userDashboard.setUserOrders(orders);
        userDashboard.setUser(user);
        userDashboard.setAllProducts(products);
        return userDashboard;
    }


}

