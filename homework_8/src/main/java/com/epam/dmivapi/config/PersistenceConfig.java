package com.epam.dmivapi.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Log4j
public class PersistenceConfig {
    @Bean
    DataSource dataSource() {
        final String TZ = "EST5EDT";

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/Library?serverTimezone=EST5EDT");
        mysqlDataSource.setUser("test");
        mysqlDataSource.setPassword("test");
        try {
            mysqlDataSource.setServerTimezone(TZ);
        } catch (SQLException exception) {
            log.error("Error while setting Timezone to " + TZ, exception);
            throw new RuntimeException("Error while setting Timezone to " + TZ);
        }
        return mysqlDataSource;
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new JdbcTransactionManager(dataSource());
    }
}
