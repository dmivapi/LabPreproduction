package com.epam.dmivapi.integration;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderResultList {
    private static final Logger LOGGER = Logger.getLogger(OrderResultList.class);
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        LOGGER.trace("addOrder invoked for order: " + order);
        orders.add(order);
    }

    public void clear() {
        orders.clear();
    }
}
