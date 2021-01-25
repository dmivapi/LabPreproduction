package com.epam.dmivapi.mongo.service;

import com.epam.dmivapi.mongo.Generator;
import com.epam.dmivapi.mongo.entity.Account;
import com.epam.dmivapi.mongo.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceIT {

    @Autowired
    CustomerService service;

    @Test
    public void createCustomer() {
        //Given
        Customer customer = Generator.generateCustomer();

        //When
        service.createCustomer(customer);

        //Then
        Customer foundCustomer = service.findCustomerById(customer.getId());
        assertCustomer(foundCustomer, customer);
    }

    @Test
    public void updateCustomer() {
        //Given
        Customer customer = Generator.generateCustomer();
        service.createCustomer(customer);

        //When
        customer.setFirstName(customer.getFirstName() + " updated");
        service.updateCustomer(customer);

        //Then
        Customer foundCustomer = service.findCustomerById(customer.getId());
        assertCustomer(foundCustomer, customer);
    }

    @Test
    public void findCustomerById() {
        //Given
        Customer customer = Generator.generateCustomer();
        service.createCustomer(customer);

        //When
        Customer foundCustomer = service.findCustomerById(customer.getId());

        //Then
        assertCustomer(foundCustomer, customer);
    }

    @Test
    public void findCustomerByFirstNameAndByLastName() {
        //Given
        Customer customer = Generator.generateCustomer();
        service.createCustomer(customer);

        //When
        List<Customer> foundCustomers = service.findCustomerByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());

        //Then
        for (Customer foundCustomer : foundCustomers) {
            assertCustomer(foundCustomer, customer);
        }
    }

    @Test
    public void findCustomerByAddressList() {
        //Given
        Customer customer = Generator.generateCustomer();
        service.createCustomer(customer);

        //When
        List<Customer> foundCustomers = service.findCustomerByAddresses(customer.getAddresses().get(0));

        //Then
        for (Customer foundCustomer : foundCustomers) {
            assertCustomer(foundCustomer, customer);
        }
    }

    @Test
    public void findCustomerByCardNumber() {
        //Given
        Customer customer = Generator.generateCustomer();
        service.createCustomer(customer);

        //When
        Customer foundCustomer = service.findCustomerByCardNumber(customer.getAccounts().get(0).getCardNumber());

        //Then
        assertCustomer(foundCustomer, customer);
    }

    @Test
    public void findCustomersWithAnyCardExpired() {
        //Given
        Customer customer = Generator.generateCustomer();
        service.createCustomer(customer);

        //When
        List<Customer> foundCustomers = service.findCustomersWithAnyCardExpired();

        //Then
        for (Customer foundCustomer : foundCustomers) {
            assertTrue(hasExpiredCard(foundCustomer.getAccounts()));
        }
    }

    private boolean hasExpiredCard(List<Account> accounts) {
        for (Account account : accounts) {
            if (account.getExpirationDate().isBefore(LocalDate.now()))
                return true;
        }
        return false;
    }

    private void assertCustomer(Customer actualCustomer, Customer expectedCustomer) {
        assertThat(actualCustomer.getId(), is(expectedCustomer.getId()));
        assertThat(actualCustomer.getFirstName(), is(expectedCustomer.getFirstName()));
        assertThat(actualCustomer.getLastName(), is(expectedCustomer.getLastName()));
        assertThat(actualCustomer.getAddresses().size(), is(expectedCustomer.getAddresses().size()));
        assertThat(actualCustomer.getAddresses(), contains(expectedCustomer.getAddresses().toArray()));
        assertThat(actualCustomer.getAccounts().size(), is(expectedCustomer.getAccounts().size()));
        assertThat(actualCustomer.getAccounts(), contains(expectedCustomer.getAccounts().toArray()));
    }
}