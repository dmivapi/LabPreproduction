package com.epam.dmivapi;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SystemArchitectureAspect {
    @Pointcut("within(com.epam.dmivapi.controller..*)")
    public void inControllerLayer() {}

    @Pointcut("within(com.epam.dmivapi.dao..*)")
    public void inDaoLayer() {}

    @Pointcut("within(com.epam.dmivapi.service..*)")
    public void inServiceLayer() {}
}
