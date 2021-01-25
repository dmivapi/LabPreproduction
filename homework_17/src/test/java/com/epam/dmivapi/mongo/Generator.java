package com.epam.dmivapi.mongo;

import com.epam.dmivapi.mongo.entity.Account;
import com.epam.dmivapi.mongo.entity.Address;
import com.epam.dmivapi.mongo.entity.Customer;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class Generator {
    private final Random random = new Random();
    public Customer generateCustomer() {
        int suffix = random.nextInt(10000);
        return new Customer(
                UUID.randomUUID(),
                "firstName" + suffix,
                "lastName" + suffix,
                Collections.singletonList(
                        new Address(
                                "line1" + suffix,
                                "line2" + suffix,
                                "countryCode" + suffix
                        )
                ),
                Arrays.asList(
                        new Account(
                                "1111-2222-3333-4444-" + suffix,
                                "CoolClient" + suffix,
                                LocalDate.now().plusDays(1)
                        ),
                        new Account(
                                "1111-2222-3333-4444-" + suffix,
                                "CoolClient" + suffix,
                                LocalDate.now().minusMonths(1)
                        )
                )
        );
    }
}
