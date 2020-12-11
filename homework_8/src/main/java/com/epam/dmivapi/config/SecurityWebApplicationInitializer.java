package com.epam.dmivapi.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(value = Ordered.LOWEST_PRECEDENCE)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}
