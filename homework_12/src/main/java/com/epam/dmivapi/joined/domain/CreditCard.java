package com.epam.dmivapi.joined.domain;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class CreditCard extends BillingDetails {
    String cardNumber;
    int expYear;
    int expMonth;
}
