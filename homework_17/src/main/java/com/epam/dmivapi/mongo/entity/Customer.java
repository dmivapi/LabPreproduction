package com.epam.dmivapi.mongo.entity;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Customer {
    UUID id;
    String firstName;
    String lastName;
    List<Address> addresses;
    List<Account> accounts;
}
