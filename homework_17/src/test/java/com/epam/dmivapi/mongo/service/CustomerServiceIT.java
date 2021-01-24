package com.epam.dmivapi.mongo.service;

import com.epam.dmivapi.mongo.Generator;
import com.epam.dmivapi.mongo.entity.Customer;
import com.epam.dmivapi.mongo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CustomerServiceIT {

    @Autowired
    CustomerRepository repository;

    @Test
    void createCustomer() {
        Customer customer = Generator.generateCustomer();
        repository.save(customer);

        Customer foundCustomer = repository.findById(customer.getId()).get();

        assertThat(foundCustomer.getId(), is(customer.getId()));
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void findCustomerById() {
    }

    @Test
    void findCustomerBy() {
    }

    @Test
    void findCustomerByAddressList() {
    }

    @Test
    void findCustomerByCardNumber() {
    }

    @Test
    void findCustomersWithAnyCardExpired() {
    }
}