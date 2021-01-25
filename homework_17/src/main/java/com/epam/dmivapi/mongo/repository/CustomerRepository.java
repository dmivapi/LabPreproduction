package com.epam.dmivapi.mongo.repository;

import com.epam.dmivapi.mongo.entity.Address;
import com.epam.dmivapi.mongo.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, UUID> {
    Customer findCustomerById(UUID id);

    List<Customer> findCustomerByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findCustomerByAddresses(List<Address> address);

    @Query("{ 'accounts.cardNumber' : ?0 }")
    Customer findCustomerByCardNumber(String cardNumber);

    @Query("{ 'accounts.expirationDate' : {$lt: ?0 } }")
    List<Customer> findCustomersWithAnyCardExpired(LocalDate now);
}
