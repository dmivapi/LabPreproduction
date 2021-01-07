package com.epam.dmivapi.singletable.repo;

import com.epam.dmivapi.singletable.domain.BillingDetails;
import com.epam.dmivapi.singletable.domain.Buyer;

import javax.persistence.EntityManager;
import java.util.List;

public class BillingDetailsRepository {
    private EntityManager entityManager;

    public BillingDetailsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<BillingDetails> findByBuyerId(Long id) {
        return entityManager.find(Buyer.class, id).getBillingDetails();
    }

    public void create(BillingDetails billingDetails) {
        entityManager.getTransaction().begin();
        entityManager.persist(billingDetails);
        entityManager.getTransaction().commit();
    }
}
