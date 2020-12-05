package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.model.User;
import com.epam.dmivapi.repository.impl.UserRepositoryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static java.util.Objects.isNull;
import static org.springframework.security.core.userdetails.User.*;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = UserRepositoryImpl.findUserByLogin(s);
        if (isNull(user))
            throw new UsernameNotFoundException("User: " + s + " was not found");

        return withUsername(user.getEmail())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .roles(user.getUserRole().toString())
                .build();
    }
}
