package com.brij;

import com.brij.model.Order;
import com.brij.model.Product;
import com.brij.model.User;
import com.brij.model.UserDashboard;
import com.brij.service.OrderService;
import com.brij.service.ProductService;
import com.brij.service.UserService;

import java.util.*;
import java.util.concurrent.*;

public class CompletableFutureExecutor implements MyExecutor {
    OrderService orderService;
    UserService userService;
    ProductService productService;

    public CompletableFutureExecutor(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }


    @Override
    public void execute(long milliseconds) throws InterruptedException {
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> executeMe(milliseconds));
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> executeMe(milliseconds));
        CompletableFuture.allOf(cf1, cf2).join();
    }

    public Order createOrder(int userId, int productId) throws InterruptedException {
        CompletableFuture<Product> productFuture = CompletableFuture.supplyAsync(() -> productService.getProduct(productId));
        CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() -> userService.getUser(userId));
        final CompletableFuture<Order> orderFuture = CompletableFuture.allOf(productFuture, userFuture).thenApplyAsync(u -> {
            User user = userFuture.join();
            Product product = productFuture.join();
            return orderService.addOrder(user.getId(), product.getId());
        });

        return orderFuture.join();

    }

    public UserDashboard getDashBoard(int userId) throws InterruptedException {
        CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() -> userService.getUser(userId));
        CompletableFuture<List<Order>> userOrdersFuture = CompletableFuture.supplyAsync(() -> orderService.getOrder(userId)).exceptionally(e -> new ArrayList<>());
        CompletableFuture<Set<Product>> productsFuture = CompletableFuture.supplyAsync(() -> productService.getProducts()).exceptionally(e -> new HashSet<>());


        CompletableFuture<UserDashboard> resultFuture = CompletableFuture.allOf(userFuture, userOrdersFuture, productsFuture).thenApplyAsync(f -> {
            final UserDashboard userDashboard = new UserDashboard();
            userDashboard.setUser(userFuture.join());
            userDashboard.setUserOrders(userOrdersFuture.join());
            userDashboard.setAllProducts(productsFuture.join());
            return userDashboard;
        });
        return resultFuture.join();
    }


}