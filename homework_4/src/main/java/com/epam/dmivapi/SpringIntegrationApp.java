package com.epam.dmivapi;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SpringIntegrationApp {
    public static final String ORDER_FILE = "orders.csv";

    public static void main(String[] args) throws FileNotFoundException {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringIntegrationConfig.class);

        SpringIntegrationConfig.InputOrderGateway gateway =
                ctx.getBean(SpringIntegrationConfig.InputOrderGateway.class);

        OrderResultList orderResultList =
                ctx.getBean(OrderResultList.class);

        try (CSVReader csvReader = new CSVReader(new FileReader(ORDER_FILE));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                gateway.placeOrder(Arrays.asList(values));
            }
            System.out.println(orderResultList);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}