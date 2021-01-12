package com.epam.dmivapi.springbootdemo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AppNameHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().withDetail("Application name", "homework_14").build();
    }
}
