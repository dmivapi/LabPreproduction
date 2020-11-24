package com.epam.dmivapi.integration;

import java.util.List;

public interface OrderResultList extends List<Order> {
    void addOrder(Order order);
}
