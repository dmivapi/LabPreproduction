package com.epam.dmivapi.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.sql.DataSource;

public class BatchConfiguration {
    private static final String QUERY_USER_BALANCE = "SELECT * FROM user_balance";

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<UserBalance> jdbcReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<UserBalance>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY_USER_BALANCE)
                .rowMapper(new BeanPropertyRowMapper<>(UserBalance.class))
                .build();
    }

    @Bean
    public UserBalanceProcessor lowBalanceProcessor() {
        return new UserBalanceProcessor();
    }

    @Bean
    public ItemWriter<SimpleMailMessage> simpleEmailWriter(MailSender javaMailSender) {
        SimpleMailMessageItemWriter writer = new SimpleMailMessageItemWriter();
        writer.setMailSender(javaMailSender);
        return writer;
    }

    @Bean
    public Step step1(
            ItemReader<UserBalance> reader,
            UserBalanceProcessor processor,
            ItemWriter<SimpleMailMessage> writer){
        return stepBuilderFactory.get("step1")
                .<UserBalance, UserBalance>chunk(2)
                .reader(reader)
                //.processor(processor)
                //.writer(writer)
                .build();
    }
}
