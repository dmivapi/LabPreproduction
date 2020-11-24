package com.epam.dmivapi;

import java.util.List;

public interface OrderResultList extends List<Order> {
    void addOrder(Order order);
}
