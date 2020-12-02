package com.epam.dmivapi.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceC {
    public void doSomeServiceCBusiness() {
        System.out.println("doSomeServiceCBusiness - invoked");
        throw new RuntimeException("@Component ServiceC::doSomeServiceCBusiness RuntimeException");
    }

    public void doSomeOtherServiceCBusiness() {
        System.out.println("doSomeOtherServiceCBusiness - invoked");
    }
}
