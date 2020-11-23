package com.epam.dmivapi;

import java.util.List;

public class Order {
    enum State {
        CANCELLED, WAITING_FOR_PAYMENT, PAYMENT_COMPLETED
    }

    private int id;
    private State orderState;
    private String comment;

    public static Order createOrder(List<String> fields) {
        Order order = new Order();
        order.id = Integer.parseInt(fields.get(0));
        order.orderState = State.valueOf(fields.get(1));
        order.comment = fields.get(2);
        return order;
    }

    public State getOrderState() {
        return orderState;
    }
    public boolean isNotCancelled() { return orderState != State.CANCELLED;}
    public void setOrderState(State orderState) {
        this.orderState = orderState;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderState=" + orderState +
                ", comment='" + comment + '\'' +
                '}';
    }
}
