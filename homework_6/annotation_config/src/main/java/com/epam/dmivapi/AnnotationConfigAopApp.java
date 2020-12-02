package com.epam.dmivapi;

import com.epam.dmivapi.controller.BController;
import com.epam.dmivapi.dao.ADao;
import com.epam.dmivapi.dao.BDao;
import com.epam.dmivapi.dao.CDao;
import com.epam.dmivapi.service.ServiceB;
import com.epam.dmivapi.service.ServiceC;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AnnotationConfigAopApp {
    public static void main(String[] args) {
        PropertyConfigurator.configure("homework_6/annotation_config/src/main/resources/log4j.properties");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfigAopApp.class);

        BController bController = ctx.getBean(BController.class);
        ADao aDao = ctx.getBean(ADao.class);
        BDao bDao = ctx.getBean(BDao.class);
        CDao cDao = ctx.getBean(CDao.class);
        ServiceB serviceB = ctx.getBean(ServiceB.class);
        ServiceC serviceC = ctx.getBean(ServiceC.class);

        bController.getById(5L);
        bController.getByName("Somename");

        aDao.findAll();
        aDao.findOne();

        bDao.getAll();
        bDao.findOne();

        cDao.findAll();
        cDao.findOne();

        try {
            serviceB.doSomeServiceBBusiness();
        } catch (RuntimeException ex) {}
        serviceB.doSomeOtherServiceBBusiness();

        try {
            serviceC.doSomeServiceCBusiness();
        } catch (RuntimeException ex) {}
        serviceC.doSomeOtherServiceCBusiness();

        ctx.close();
    }
}
