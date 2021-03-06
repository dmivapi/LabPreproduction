package com.epam.dmivapi.singletable.domain;

import lombok.Data;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "billing_type")
@Table(name = "billing_details")
@Data
public abstract class BillingDetails {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    Buyer owner;
}
