package com.epam.dmivapi.singletable.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CARD")
@Data
public class CreditCard extends BillingDetails {
    String cardNumber;
    int expYear;
    int expMonth;
}
