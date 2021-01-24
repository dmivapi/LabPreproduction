package com.epam.dmivapi.mongo;

import com.epam.dmivapi.mongo.entity.Customer;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class Generator {
    private final Random random = new Random();
    public Customer generateCustomer() {
        return new Customer();
    }
}
