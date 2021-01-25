package com.epam.dmivapi.mongo.service.impl;

import com.epam.dmivapi.mongo.entity.Address;
import com.epam.dmivapi.mongo.entity.Customer;
import com.epam.dmivapi.mongo.repository.CustomerRepository;
import com.epam.dmivapi.mongo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository repository;

    @Override
    public void createCustomer(Customer customer) {
        repository.insert(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        repository.save(customer);
    }

    @Override
    public Customer findCustomerById(UUID id) {
        return repository.findCustomerById(id);
    }

    @Override
    public List<Customer> findCustomerByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findCustomerByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Customer> findCustomerByAddresses(Address address) {
        return repository.findCustomerByAddresses(Collections.singletonList(address));
    }

    @Override
    public Customer findCustomerByCardNumber(String cardNumber) {
        return repository.findCustomerByCardNumber(cardNumber);
    }

    @Override
    public List<Customer> findCustomersWithAnyCardExpired() {
        return repository.findCustomersWithAnyCardExpired(LocalDate.now());
    }
}
