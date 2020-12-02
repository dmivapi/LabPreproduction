package com.epam.dmivapi.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceB {
    public void doSomeServiceBBusiness() {
        System.out.println("doSomeServiceBBusiness invoked");
        throw new ServiceException("@Service ServiceB::doSomeServiceCBusiness ServiceException");
    }

    public void doSomeOtherServiceBBusiness() {
        System.out.println("doSomeOtherServiceBBusiness invoked");
    }
}
