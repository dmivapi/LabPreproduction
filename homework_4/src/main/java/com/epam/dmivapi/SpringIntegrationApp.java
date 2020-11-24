package com.epam.dmivapi;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SpringIntegrationApp {
    private static final Logger LOGGER = Logger.getLogger(SpringIntegrationApp.class);
    private static final String ORDER_FILE = "homework_4/orders.csv";

    public static void main(String[] args) throws FileNotFoundException {
        PropertyConfigurator.configure("homework_4/src/main/resources/log4j.properties");
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringIntegrationConfig.class);

        SpringIntegrationConfig.InputOrderGateway gateway =
                ctx.getBean(SpringIntegrationConfig.InputOrderGateway.class);

        OrderResultList orderResultList =
                ctx.getBean(OrderResultList.class);

        try (CSVReader csvReader = new CSVReader(new FileReader(ORDER_FILE));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                LOGGER.trace("Placing Order fields into messaging gateway: " + Arrays.toString(values));
                gateway.placeOrder(Arrays.asList(values));
            }
            System.out.println(orderResultList);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}