package com.epam.dmivapi.config;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Configuring security");
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/book").permitAll()
                .antMatchers("/book/list").permitAll()
                .antMatchers("/loan").hasAnyRole("READER", "LIBRARIAN")
                .antMatchers("/user").hasAnyRole("LIBRARIAN", "ADMIN")
                .anyRequest()
                .authenticated();
    }
}
