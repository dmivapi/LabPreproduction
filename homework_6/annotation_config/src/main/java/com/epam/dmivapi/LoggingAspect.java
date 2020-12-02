package com.epam.dmivapi;

import com.epam.dmivapi.dao.Loggable;
import com.epam.dmivapi.service.ServiceException;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j
public class LoggingAspect {
    @Pointcut("execution(* *All(..))")
    public void suffixAllMethods() {}

    @Before("com.epam.dmivapi.SystemArchitectureAspect.inControllerLayer() && args(param)")
    public void logReceivedId(JoinPoint joinPoint, Long param) {
        log.info(joinPoint.toString() + " Long id: " + param); }

    @AfterThrowing(pointcut = "com.epam.dmivapi.SystemArchitectureAspect.inServiceLayer()", throwing = "exc")
    public void logDedicatedServiceException(ServiceException exc) {
        log.error(exc.getMessage());
    }

    @After("suffixAllMethods() && @annotation(loggable)")
    public void logSuffixAllRepositoryMethods(Loggable loggable) {
        log.info(loggable.toString());
    }
}
