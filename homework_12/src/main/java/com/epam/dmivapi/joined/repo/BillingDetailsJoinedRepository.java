package com.epam.dmivapi.joined.repo;

import com.epam.dmivapi.joined.domain.BillingDetails;
import com.epam.dmivapi.joined.domain.Buyer;

import javax.persistence.EntityManager;
import java.util.List;

public class BillingDetailsJoinedRepository {
    private EntityManager entityManager;

    public BillingDetailsJoinedRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<BillingDetails> findByBuyerId(Long id) {
        return entityManager.find(Buyer.class, id).getBillingDetails();
    }
}
