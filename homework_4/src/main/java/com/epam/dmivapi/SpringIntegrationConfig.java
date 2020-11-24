package com.epam.dmivapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import java.util.List;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class SpringIntegrationConfig {
    @Bean
    public OrderResultList orderResultList() {
        return new OrderResultListImpl();
    }

    @MessagingGateway
    public interface InputOrderGateway {
        @Gateway(requestChannel = "orderFlow.input")
        void placeOrder(List<String> fields);
    }

    @Bean
    public IntegrationFlow orderFlow() {
        return flow -> flow
                .transform(Order::createOrder)
                .filter(Order::isNotCancelled)
                .channel("filteredOrders");
    }

    @ServiceActivator(inputChannel = "filteredOrders")
    public void toList(Order order) {
        OrderResultList orderResultList = orderResultList();
        orderResultList.addOrder(order);
    }
}
