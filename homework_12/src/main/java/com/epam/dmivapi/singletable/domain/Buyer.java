package com.epam.dmivapi.singletable.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class Buyer {
    @Id
    @GeneratedValue
    Long id;
    String firstName;
    String lastName;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<BillingDetails> billingDetails;
}
