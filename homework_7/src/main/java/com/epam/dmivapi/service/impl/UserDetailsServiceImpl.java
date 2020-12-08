package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.model.CurrentUser;
import com.epam.dmivapi.model.User;
import com.epam.dmivapi.repository.impl.UserRepositoryImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = UserRepositoryImpl.findUserByLogin(s);
        if (isNull(user))
            throw new UsernameNotFoundException("User: " + s + " was not found");

        return new CurrentUser(
                user.getId(),
                user.getEmail(),
                new BCryptPasswordEncoder().encode(user.getPassword()),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().toString())),
                user.getFirstName(),
                user.getLastName(),
                user.getLocaleName(),
                user.getUserRole().toString()
        );
    }
}
