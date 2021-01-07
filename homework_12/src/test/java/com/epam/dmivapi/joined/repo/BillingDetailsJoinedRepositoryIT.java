package com.epam.dmivapi.joined.repo;

import com.epam.dmivapi.joined.BillingDetailsGenerator;
import com.epam.dmivapi.joined.BuyerGenerator;
import com.epam.dmivapi.joined.domain.BillingDetails;
import com.epam.dmivapi.joined.domain.Buyer;
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

class BillingDetailsJoinedRepositoryIT {
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.epam.dmivapi.hw12");
    static final EntityManager entityManager = emf.createEntityManager();
    BillingDetailsJoinedRepository repository = new BillingDetailsJoinedRepository(entityManager);

    @BeforeEach
    void setUp() {
        entityManager.getTransaction().begin();
    }

    @Test
    void find() {
        //Given
        final int TEST_INDEX = 3;
        Buyer buyers[] = new Buyer[TEST_INDEX + 3];
        for (int i = 0; i < buyers.length; i++) {
            Buyer buyer = BuyerGenerator.generate(i * 3);
            List<BillingDetails> billingDetailsList = BillingDetailsGenerator.generateBillingDetails(i + 3);
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