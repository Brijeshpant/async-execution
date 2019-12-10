package com.brij;

import com.brij.model.Order;
import com.brij.model.Product;
import com.brij.model.User;
import com.brij.model.UserDashboard;
import com.brij.service.OrderService;
import com.brij.service.ProductService;
import com.brij.service.UserService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreamExecutor implements MyExecutor {

    OrderService orderService;
    UserService userService;
    ProductService productService;

    public ParallelStreamExecutor(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void execute(long milliseconds) {

        Stream.of(1, 2).parallel().forEach(i -> executeMe(milliseconds));
    }

    public Order createOrder(int userId, int productId) {
        HashMap<Command, Object> executionCommands = new HashMap<>();
        Command userCommand = p -> userService.getUser((int) p);
        Command productCommand = p -> productService.getProduct((int) p);
        executionCommands.put(userCommand, userId);
        executionCommands.put(productCommand, productId);
        List<Object> result = Stream.of(productCommand, userCommand).parallel().map(k -> k.execute(executionCommands.get(k))).collect(Collectors.toList());
        try {
            Product product = (Product) result.get(0);
            User user = (User) result.get(1);
            if (Objects.nonNull(product) && Objects.nonNull(user)) {
                return orderService.addOrder(product.getId(), user.getId());
            }
        } catch (Exception e) {
        }
        throw new RuntimeException("Failed to process create order");
    }


    @Override
    public UserDashboard getDashBoard(int userId) throws InterruptedException {

        HashMap<Command, Object> executionCommands = new HashMap<>();
        Command userCommand = p -> userService.getUser((int) p);
        executionCommands.put(userCommand, userId);
        Command orderCommand = p -> getOrder((int) p);
        executionCommands.put(orderCommand, userId);
        Command productCommand = p -> getProducts();
        executionCommands.put(productCommand, null);

        List<Object> result = Stream.of(userCommand, orderCommand, productCommand).parallel().map(k -> k.execute(executionCommands.get(k))).collect(Collectors.toList());

        try {
            User user = (User) result.get(0);
            List<Order> order = (List<Order>) result.get(1);
            Set<Product> product = (Set<Product>) result.get(2);
            UserDashboard userDashboard = new UserDashboard();
            userDashboard.setUser(user);
            userDashboard.setUserOrders(order);
            userDashboard.setAllProducts(product);
            return userDashboard;
        } catch (Exception e) {
            new RuntimeException("Failed to get Dashboard details");
        }
        return null;

    }

    private Set<Product> getProducts() {
        try {
            return productService.getProducts();
        } catch (RuntimeException e) {

        }
        return new HashSet<>();
    }


    private List<Order> getOrder(int p) {
        try {
            return orderService.getOrder(p);
        } catch (RuntimeException e) {
        }
        return new ArrayList<>();
    }

}

interface Command<R, T> {
    R execute(T t);
}
