package com.epam.dmivapi.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.sql.DataSource;

public class BatchConfiguration {
    private static final String QUERY_USER_BALANCE = "SELECT * FROM user_balance";
    @Bean
    public ItemReader<UserBalance> itemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<UserBalance>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY_USER_BALANCE)
                .rowMapper(new BeanPropertyRowMapper<>(UserBalance.class))
                .build();
    }

    @Bean
    public UserBalanceProcessor processor() {
        return new UserBalanceProcessor();
    }

    @Bean
    public ItemWriter<SimpleMailMessage> simpleEmailWriter(MailSender javaMailSender) {
        SimpleMailMessageItemWriter writer = new SimpleMailMessageItemWriter();
        writer.setMailSender(javaMailSender);
        return writer;
    }
}
