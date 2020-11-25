package com.epam.dmivapi.integration;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class SpringIntegrationConfig {
    private static final Logger LOGGER = Logger.getLogger(SpringIntegrationConfig.class);

    @Bean
    public OrderResultList orderResultList() {
        LOGGER.trace("Entering orderResultList()");
        return new OrderResultList();
    }

    @MessagingGateway
    public interface InputOrderGateway {
        @Gateway(requestChannel = "orderFlow.input")
        void placeOrder(List<String> fields);
    }

    @Bean
    public IntegrationFlow orderFlow() {
        LOGGER.trace("Entering orderFlow()");
        return flow -> flow
                .transform(Order::createOrder)
                .filter(Order::isNotCancelled)
                .channel("filteredOrders");
    }

    @ServiceActivator(inputChannel = "filteredOrders")
    public void addToInMemoryList(Order order) {
        LOGGER.trace("Entering addToInMemoryList()");
        OrderResultList orderResultList = orderResultList();
        orderResultList.addOrder(order);
    }
}
