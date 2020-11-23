package com.epam.dmivapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.List;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class SpringIntegrationConfig {
    @Bean
    DirectChannel outputChannel() {
        return new DirectChannel();
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
                .channel("outputChannel");
    }
}
