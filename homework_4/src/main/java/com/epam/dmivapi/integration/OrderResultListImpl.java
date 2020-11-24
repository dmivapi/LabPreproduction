package com.epam.dmivapi.integration;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class OrderResultListImpl extends ArrayList<Order> implements OrderResultList {
    private static final Logger LOGGER = Logger.getLogger(OrderResultListImpl.class);
    @Override
    public void addOrder(Order order) {
        LOGGER.trace("addOrder invoked for order: " + order);
        add(order);
    }
}
