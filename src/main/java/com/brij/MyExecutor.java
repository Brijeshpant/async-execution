package com.brij;

import com.brij.model.Order;
import com.brij.model.UserDashboard;

public interface MyExecutor {

    public void execute(long milliseconds) throws InterruptedException;

    default void executeMe(long timeToSleep) {
        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    Order createOrder(int userId, int productId) throws InterruptedException;

    UserDashboard getDashBoard(int userId) throws InterruptedException;


}