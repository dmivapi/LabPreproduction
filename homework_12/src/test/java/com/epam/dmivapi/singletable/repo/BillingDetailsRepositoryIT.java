package com.epam.dmivapi.singletable.repo;

import com.epam.dmivapi.singletable.BillingDetailsGenerator;
import com.epam.dmivapi.singletable.BuyerGenerator;
import com.epam.dmivapi.singletable.domain.BillingDetails;
import com.epam.dmivapi.singletable.domain.Buyer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

class BillingDetailsRepositoryIT {
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.epam.dmivapi.hw12");
    static final EntityManager entityManager = emf.createEntityManager();
    BillingDetailsRepository repository = new BillingDetailsRepository(entityManager);

    @BeforeEach
    void setUp() {
        entityManager.getTransaction().begin();
    }

    @Test
    void find() {
        //Given
        Buyer buyer = BuyerGenerator.generate(17);
        List<BillingDetails> billingDetailsList = BillingDetailsGenerator.generateBillingDetails(5);
        billingDetailsList.forEach(item -> item.setOwner(buyer));
        buyer.setBillingDetails(billingDetailsList);
        entityManager.persist(buyer);
        entityManager.getTransaction().commit();

        //When
        List<BillingDetails> foundBillingDetails = repository.findByBuyerId(buyer.getId());

        //Then
        assertThat(foundBillingDetails.size(), is(billingDetailsList.size()));
        assertThat(foundBillingDetails, contains(billingDetailsList.toArray()));
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void afterAll() {
        entityManager.close();
        emf.close();
    }
}