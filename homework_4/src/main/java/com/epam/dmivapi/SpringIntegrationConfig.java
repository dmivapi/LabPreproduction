package com.epam.dmivapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
public class SpringIntegrationConfig {

    @Bean
    IntegrationFlow orderFlow() {
        IntegrationFlow.from(File)
    }


    @MessagingGateway
    public interface InputOrderGateway {
        @Gateway(requestChannel = "orders.input")
        void placeOrder(Order order);
    }
}
