package com.epam.dmivapi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringIntegrationApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringIntegrationConfig.class);
        SpringIntegrationConfig.InputOrderGateway orderGateway =
                ctx.getBean(SpringIntegrationConfig.InputOrderGateway.class);

        for (int i = 0; i < 1000; i++) {
            orderGateway.placeOrder(Order.createOrder());
        }
    }
}
