package com.epam.dmivapi.mongo.config;

/*@Configuration
@ComponentScan(value = "com.epam.dmivapi")
public class MongoTestConfig extends AbstractMongoClientConfiguration {
    private static final String DB_NAME = "customer-db";
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/" + DB_NAME);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.epam.dmivapi.mongo");
    }

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }
}*/

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collection;
import java.util.Collections;

@Configuration
@ComponentScan(value = "com.epam.dmivapi.mongo")
@EnableTransactionManagement
public class MongoTestConfig extends AbstractMongoClientConfiguration {
    private static final String DB_NAME = "customer-db";
    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/" + DB_NAME);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.epam.dmivapi.mongo");
    }
}
