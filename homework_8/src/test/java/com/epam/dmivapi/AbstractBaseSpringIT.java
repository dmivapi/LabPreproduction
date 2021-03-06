package com.epam.dmivapi;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
public abstract class AbstractBaseSpringIT {
    @Autowired
    protected MockMvc mockMvc;

    @Configuration
    @EnableWebMvc
    @ComponentScan("com.epam.dmivapi")
    protected static class TestContextConfiguration {
        @Bean
        MockMvc mockMvc(WebApplicationContext webApplicationContext) {
            return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .alwaysDo(print())
                    .build();
        }
    }
}
