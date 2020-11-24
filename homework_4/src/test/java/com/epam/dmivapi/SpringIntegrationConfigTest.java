package com.epam.dmivapi;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpringIntegrationConfigTest {
    private static final Logger LOGGER = Logger.getLogger(SpringIntegrationConfigTest.class);
    private static final String ORDER_FILE = "orders.csv";

    private static final AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringIntegrationConfig.class);

    private static final SpringIntegrationConfig.InputOrderGateway gateway =
            ctx.getBean(SpringIntegrationConfig.InputOrderGateway.class);

    private static final OrderResultList orderResultList =
            ctx.getBean(OrderResultList.class);

    @BeforeAll
    static void beforeAll() {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }

    @BeforeEach
    void setUp() {
        orderResultList.clear();
    }

    @Test
    void shouldInitializeContext() {
        assertNotNull(ctx);
    }

    @Test
    void shouldInitializeMessagingGateWay() {
        assertNotNull(gateway);
    }

    @Test
    void shouldInitializeOrderResultList() {
        assertNotNull(orderResultList);
    }

    @Test
    void shouldFilterCancelledOrders() {
        List<List<String>> csvData = new ArrayList<>();

        csvData.add(Arrays.asList("5","CANCELLED", "Hello"));
        csvData.add(Arrays.asList("8","PAYMENT_COMPLETED","Goodbye"));
        csvData.add(Arrays.asList("9","WAITING_FOR_PAYMENT", "Pay now"));
        csvData.add(Arrays.asList("11","CANCELLED", "Hello 1"));
        csvData.add(Arrays.asList("18","PAYMENT_COMPLETED","Goodbye 1"));
        csvData.add(Arrays.asList("19","WAITING_FOR_PAYMENT", "Pay now 1"));
        csvData.add(Arrays.asList("211","CANCELLED", "Hello 1"));
        csvData.add(Arrays.asList("218", "PAYMENT_COMPLETED", "Goodbye 1"));
        csvData.add(Arrays.asList("219","WAITING_FOR_PAYMENT", "Pay now 1"));

        for (List<String> fields : csvData) {
            gateway.placeOrder(fields);
        }

        List<Order> filteredOrders = new ArrayList<>();
        for (List<String> fields : csvData) {
            Order order = Order.createOrder(fields);
            if (order.isNotCancelled())
                filteredOrders.add(order);
        }

        assertIterableEquals(filteredOrders, orderResultList);
    }
}