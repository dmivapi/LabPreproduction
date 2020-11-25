package com.epam.dmivapi.batch;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;

public class UserBalanceProcessor implements ItemProcessor<UserBalance, SimpleMailMessage> {
    private static final String FROM = "dpua1611@gmail.com";
    private static final int BALANCE_TRESHOLD = 10;

    @Override
    public SimpleMailMessage process(UserBalance userBalance) throws Exception {
        if (userBalance.getBalance() >= BALANCE_TRESHOLD)
            return null;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(FROM);
        message.setTo(userBalance.getEmail());
        message.setSubject("Account balance notification");
        message.setText(
                "Dear " +
                        userBalance.getName() +
                        ",\n\n" +
                        "Your balance is less then $10, currently: " +
                        userBalance.getBalance() +
                        "\n\n" +
                        "This message was generated automatically, please don't reply to it."
                );
        return message;
    }
}
