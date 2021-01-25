package com.epam.dmivapi.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Account {
    String cardNumber;
    String nameOnAccount;
    LocalDate expirationDate;
}
