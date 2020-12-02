package com.epam.dmviapi;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitectureAspect {
    @Pointcut("within(com.epam.dmivapi.controller..*)")
    public void inControllerLayer() {}

    @Pointcut("within(com.epam.dmivapi.dao..*)")
    public void inDaoLayer() {}

    @Pointcut("within(com.epam.dmivapi.service..*)")
    public void inServiceLayer() {}
}
