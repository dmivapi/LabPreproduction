package com.epam.dmivapi.batch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    Logger LOGGER = Logger.getLogger(BatchConfiguration.class);
    private static final String QUERY_USER_BALANCE = "SELECT * FROM user_balance;";

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JavaMailSender emailSender;

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
    public ItemWriter<SimpleMailMessage> simpleEmailWriter() {
        SimpleMailMessageItemWriter writer = new SimpleMailMessageItemWriter();
        writer.setMailSender(emailSender);
        return writer;
    }

    @Bean
    public Step checkUserLowBalanceAndWarnByEmailStep(
            ItemReader<UserBalance> reader,
            UserBalanceProcessor processor,
            ItemWriter<SimpleMailMessage> writer){
        return stepBuilderFactory.get("checkUserLowBalanceAndWarnByEmailStep")
                .<UserBalance, SimpleMailMessage>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job notifyUsersWithLowBalanceJob(Step step1) {
        return jobBuilderFactory.get("notifyUsersWithLowBalanceJob")
                .start(step1)
                .build();
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/UserFinance?serverTimezone=EST5EDT";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername("test");
        dataSource.setPassword("test");

        return dataSource;
    }

    @Bean
    JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("ab4d5a7538bf16");
        mailSender.setPassword("7b8b4fab7f2d0f");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
