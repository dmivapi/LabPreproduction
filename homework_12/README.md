Domain model:
```
class BillingDetails {
	Long id;
	Owner owner;
}
```

```
class CreditCard extends BillingDetails {
	String cardNumber;
	int expYear;
	int expMonth;
}
```

```
class BankAccount extends BillingDetails {
	String account;
	String bandName;
}
```

```
class Buyer {
	Long id;
	String firstName;
	String lastName;
	List<BilligDetails> billingDetails;
}
```
1. Create a repository that has method that returns all billing details for given buyer id;
1. Create two projects:
    - In the first one use single table inheritance strategy from BillingDetails hierarchy
    - In the second one use join table inheritance strategy from BillingDetails hierarchy
2. Create a repository that has method that returns all billing details for given buyer id
3. Create integrations tests for repository method.