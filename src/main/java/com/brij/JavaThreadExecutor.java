package com.brij;

import com.brij.model.*;
import com.brij.service.OrderService;
import com.brij.service.ProductService;
import com.brij.service.UserService;

import java.util.*;
import java.util.concurrent.*;

public class JavaThreadExecutor implements MyExecutor {
    OrderService orderService;
    UserService userService;
    ProductService productService;

    public JavaThreadExecutor(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }


    @Override
    public void execute(long milliseconds) throws InterruptedException {
        Thread thread1 = new Thread(() -> executeMe(milliseconds));
        Thread thread2 = new Thread(() -> executeMe(milliseconds));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Order createOrder(int userId, int productId) throws InterruptedException {
        Callable<Product> productCallable = () -> productService.getProduct(productId);
        Callable<User> userCallable = () -> userService.getUser(userId);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        ArrayList tasks = new ArrayList<Callable<Object>>();
        tasks.add(productCallable);
        tasks.add(userCallable);
        final List<Future<Object>> list = executor.invokeAll(tasks);
        executor.shutdown();
        try {
            Product product = (Product) list.get(0).get();
            User user = (User) list.get(1).get();
            if (Objects.nonNull(product) && Objects.nonNull(user)) {
                return orderService.addOrder(userId, productId);
            }
        } catch (Exception ignored) {
        }
        throw new RuntimeException("Failed to process create order");
    }

    public UserDashboard getDashBoard(int userId) throws InterruptedException {
        Callable<User> userCallable = () -> userService.getUser(userId);
        Callable<List<Order>> orderCallable = () -> orderService.getOrder(userId);
        Callable<Set<Product>> productCallable = () -> productService.getProducts();
        ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(2);
        User user;
        Set<Product> products;
        List<Order> orders;

        try {
            user = userCallable.call();
        } catch (Exception e) {
            throw new RuntimeException("Faild to get Dashboard");
        }
        ArrayList tasks = new ArrayList<Callable<Object>>();
        tasks.add(orderCallable);
        tasks.add(productCallable);
        final List<Future<Object>> list = executor.invokeAll(tasks);
        executor.shutdown();
        try {
            products = (Set<Product>) list.get(1).get();
        } catch (Exception e) {
            products = new HashSet<>();
        }
        try {
            orders = (List<Order>) list.get(0).get();
        } catch (Exception e) {
            orders = new ArrayList<>();

        }
        final UserDashboard userDashboard = new UserDashboard();

        userDashboard.setUser(user);
        userDashboard.setUserOrders(orders);
        userDashboard.setAllProducts(products);
        return userDashboard;

    }


}