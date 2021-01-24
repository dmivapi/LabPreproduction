package com.epam.dmivapi.mongo.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Account {
    String cardNumber;
    String nameOnAccount;
    LocalDate expirationDate;
}
