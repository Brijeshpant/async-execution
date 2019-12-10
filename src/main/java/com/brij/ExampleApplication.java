package com.brij;

import com.brij.model.Order;
import com.brij.model.UserDashboard;

public class ExampleApplication {

    MyExecutor executor;

    public ExampleApplication(MyExecutor executor) {
        this.executor = executor;
    }

    public void executeWithNoResult(long timeOfExecution) throws InterruptedException {
        executor.execute(timeOfExecution);
    }

    public Order createOrder(int userId, int productId) throws InterruptedException {
        return executor.createOrder(userId, productId);
    }

    public UserDashboard getDashBoard(int userId) throws InterruptedException {
        return executor.getDashBoard(userId);
    }

}