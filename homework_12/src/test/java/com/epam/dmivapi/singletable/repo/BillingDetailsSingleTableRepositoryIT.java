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

class BillingDetailsSingleTableRepositoryIT {
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.epam.dmivapi.hw12");
    static final EntityManager entityManager = emf.createEntityManager();
    BillingDetailsSingleTableRepository repository = new BillingDetailsSingleTableRepository(entityManager);

    @BeforeEach
    void setUp() {
        entityManager.getTransaction().begin();
    }

    @Test
    void find() {
        //Given
        final int TEST_INDEX = 5;
        com.epam.dmivapi.singletable.domain.Buyer buyers[] = new com.epam.dmivapi.singletable.domain.Buyer[TEST_INDEX + 3];
        for (int i = 0; i < buyers.length; i++) {
            Buyer buyer = BuyerGenerator.generate(i * 3);
            List<com.epam.dmivapi.singletable.domain.BillingDetails> billingDetailsList = BillingDetailsGenerator.generateBillingDetails(i + 3);
            billingDetailsList.forEach(item -> item.setOwner(buyer));
            buyer.setBillingDetails(billingDetailsList);
            entityManager.persist(buyer);
            buyers[i] = buyer;
        }

        //When
        List<BillingDetails> foundBillingDetails = repository.findByBuyerId(buyers[TEST_INDEX].getId());

        //Then
        assertThat(foundBillingDetails.size(), is(buyers[TEST_INDEX].getBillingDetails().size()));
        assertThat(foundBillingDetails, contains(buyers[TEST_INDEX].getBillingDetails().toArray()));
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().rollback();
    }

    @AfterAll
    static void afterAll() {
        entityManager.close();
        emf.close();
    }
}