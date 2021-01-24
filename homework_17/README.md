Create project, that uses MongoDb contains 3 entities:
1. Customer (id, firstName, lastName, addresses, accounts)
2. Account (cardNumber, nameOnAccount, expirationDate)
3. Address (line1, line2, countryCode)

Using these entities create service, which will do these operations:
1. Create customer
2. Update customer
3. Find customer(s) by: id, firstName and lastName, address, cardNumber.
4. Find customer(s) with expired cards.

Write integration tests for this service using embedded MongoDb.