package com.epam.dmivapi.mongo.service;

import com.epam.dmivapi.mongo.entity.Address;
import com.epam.dmivapi.mongo.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void createCustomer(Customer customer);
    void updateCustomer(Customer customer);
    Customer findCustomerById(UUID id);
    List<Customer> findCustomerByFirstNameAndByLastName(String firstName, String lastName);
    List<Customer> findCustomerByAddressList(Address address);
    Customer findCustomerByCardNumber(String cardNumber);
    List<Customer> findCustomersWithAnyCardExpired();
}
