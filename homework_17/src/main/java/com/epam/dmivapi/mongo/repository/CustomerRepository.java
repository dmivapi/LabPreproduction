package com.epam.dmivapi.mongo.repository;

import com.epam.dmivapi.mongo.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CustomerRepository extends MongoRepository<Customer, UUID> {

}
