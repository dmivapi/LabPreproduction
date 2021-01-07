package com.epam.dmivapi.joined.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class BankAccount extends BillingDetails {
    String account;
    @Column(name = "bank_name")
    String bankName;
}
