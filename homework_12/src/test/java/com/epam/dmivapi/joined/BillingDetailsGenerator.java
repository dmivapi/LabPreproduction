package com.epam.dmivapi.joined;

import com.epam.dmivapi.joined.domain.BankAccount;
import com.epam.dmivapi.joined.domain.BillingDetails;
import com.epam.dmivapi.joined.domain.CreditCard;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

@UtilityClass
public class BillingDetailsGenerator {
    private final String[] BANK_NAMES = {"Chase Bank", "PrivatBank", "Morgan Bank", "EBRD"};
    private final Random random = new Random(47);

    public BankAccount generateAccount(int seed) {
        BankAccount ba = new BankAccount();
        ba.setBankName(BANK_NAMES[abs(seed) % BANK_NAMES.length]);
        ba.setAccount(RandomStringUtils.randomNumeric(10));
        return ba;
    }

    public CreditCard generateCard(int seed) {
        CreditCard cc = new CreditCard();
        cc.setCardNumber(RandomStringUtils.randomNumeric(12));
        cc.setExpMonth(abs(seed) % 12 + 1);
        cc.setExpYear(LocalDate.now().plusYears(abs(seed) % 3 + 1).getYear());
        return cc;
    }

    public List<BillingDetails> generateBillingDetails(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(s -> random.nextInt(2) == 0 ? generateAccount(s) : generateCard(s))
                .collect(Collectors.toList());
    }
}
