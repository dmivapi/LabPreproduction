package com.epam.dmivapi.config;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.security.AuthSuccessHandler;
import com.epam.dmivapi.service.impl.UserDetailsServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@Log4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Configuring security");
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/loan/self").hasRole("READER")
                    .antMatchers("/librarian/**", "/loan/**").hasRole("LIBRARIAN")
                    .antMatchers("/admin/**", "/book/enter", "/book/add", "/book/delete").hasRole("ADMIN")
                    .antMatchers("/guest/**").anonymous()
                    .antMatchers("/app/switchLocale", "/app/login", "/book/list").permitAll()
                    .and()
                .formLogin()
                    .loginPage("/app/login").permitAll()
                    .usernameParameter(ContextParam.USR_LOGIN)
                    .passwordParameter(ContextParam.USR_PASSWORD)
                    .successHandler(authSuccessHandler())
                .and()
                    .logout().permitAll()
                    .logoutSuccessUrl("/app/login");
    }
}
