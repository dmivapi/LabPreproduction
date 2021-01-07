package com.epam.dmivapi.singletable.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ACCOUNT")
@Data
public class BankAccount extends BillingDetails {
    String account;
    @Column(name = "bank_name")
    String bankName;
}
