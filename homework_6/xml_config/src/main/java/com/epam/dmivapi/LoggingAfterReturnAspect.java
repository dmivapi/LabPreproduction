package com.epam.dmivapi;

import lombok.extern.log4j.Log4j;

@Log4j
public class LoggingAfterReturnAspect {
    public void afterReturn(Object returnValue) {
        log.info("LoggingAfterReturnAspect " + returnValue);
    }
}
