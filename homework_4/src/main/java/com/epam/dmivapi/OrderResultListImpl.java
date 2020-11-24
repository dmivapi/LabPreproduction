package com.epam.dmivapi;

import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class OrderResultListImpl extends ArrayList<Order> implements OrderResultList {
    @Override
    public void addOrder(Order order) {
        add(order);
    }
}
