package com.epam.dmivapi.joined.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "billing_details_joined")
@Data
public abstract class BillingDetails {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    Buyer owner;
}
