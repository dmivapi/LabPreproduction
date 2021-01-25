package com.epam.dmivapi.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
@Data
@AllArgsConstructor
public class Customer {
    UUID id;
    String firstName;
    String lastName;
    List<Address> addresses;
    List<Account> accounts;
}
