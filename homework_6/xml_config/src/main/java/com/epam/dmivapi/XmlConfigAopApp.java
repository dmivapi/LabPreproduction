package com.epam.dmivapi;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigAopApp {
    public static void main(String[] args) {
        PropertyConfigurator.configure("homework_6/xml_config/src/main/resources/log4j.properties");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:applicationContext.xml");

        ServiceA serviceA = (ServiceA) ctx.getBean("serviceA");
        serviceA.getAllA();
        serviceA.getABySomeValue(55);
        ctx.close();
    }
}
