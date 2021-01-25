package com.epam.dmivapi.mongo.service;

import com.epam.dmivapi.mongo.Generator;
import com.epam.dmivapi.mongo.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceIT {

    @Autowired
    CustomerService service;

    @Test
    public void createCustomer() {
        Customer customer = Generator.generateCustomer();
        service.createCustomer(customer);

        Customer foundCustomer = service.findCustomerById(customer.getId());

        assertThat(foundCustomer.getId(), is(customer.getId()));
        assertThat(foundCustomer.getFirstName(), is(customer.getFirstName()));
        assertThat(foundCustomer.getLastName(), is(customer.getLastName()));
        assertThat(foundCustomer.getAddresses().size(), is(customer.getAddresses().size()));
        assertThat(foundCustomer.getAddresses(), contains(customer.getAddresses().toArray()));
        assertThat(foundCustomer.getAccounts().size(), is(customer.getAccounts().size()));
        assertThat(foundCustomer.getAccounts(), contains(customer.getAccounts().toArray()));
    }

    @Test
    public void updateCustomer() {
    }

    @Test
    public void findCustomerById() {
    }

    @Test
    public void findCustomerBy() {
    }

    @Test
    public void findCustomerByAddressList() {
    }

    @Test
    public void findCustomerByCardNumber() {
    }

    @Test
    public void findCustomersWithAnyCardExpired() {
    }
}