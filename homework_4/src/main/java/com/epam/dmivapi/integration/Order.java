package com.epam.dmivapi.integration;

import org.apache.log4j.Logger;
import java.util.List;
import java.util.Objects;

public class Order {
    private static final Logger LOGGER = Logger.getLogger(Order.class);

    enum State {
        CANCELLED, WAITING_FOR_PAYMENT, PAYMENT_COMPLETED
    }

    private int id;
    private State orderState;
    private String comment;

    public static Order createOrder(List<String> fields) {
        LOGGER.trace("createOrder() invoked");
        Order order = new Order();
        try {
            order.id = Integer.parseInt(fields.get(0));
            order.orderState = State.valueOf(fields.get(1));
            order.comment = fields.get(2);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Incoming data doesn't fit the order's fields: " + e.getMessage());
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                orderState == order.orderState &&
                comment.equals(order.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderState, comment);
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
