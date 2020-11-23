package com.epam.dmivapi;

import org.springframework.stereotype.Component;
import sun.reflect.generics.scope.Scope;

import java.util.Random;

public class Order {
    enum State {
        CANCELLED, WAITING_FOR_PAYMENT, PAYMENT_COMPLETED
    }

    private State orderState;
    private static Random random = new Random();

    public static Order createOrder() {
        int orderStatusOrdinal = random.nextInt(State.values().length + 1);
        Order order = new Order();
        order.orderState = State.values()[orderStatusOrdinal];
        return order;
    }

    public State getOrderState() {
        return orderState;
    }

    public void setOrderState(State orderState) {
        this.orderState = orderState;
    }
}
