package com.epam.dmivapi;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.channel.DirectChannel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SpringIntegrationApp {
    public static final String ORDER_FILE = "orders.csv";

    public static void main(String[] args) throws FileNotFoundException {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringIntegrationConfig.class);

        SpringIntegrationConfig.InputOrderGateway gateway =
                ctx.getBean(SpringIntegrationConfig.InputOrderGateway.class);

        DirectChannel outputChannel = ctx.getBean("outputChannel", DirectChannel.class);
        outputChannel.subscribe(x -> System.out.println(x.getPayload()));

        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(ORDER_FILE));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                gateway.placeOrder(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}