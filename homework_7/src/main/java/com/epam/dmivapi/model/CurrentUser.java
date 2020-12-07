package com.epam.dmivapi.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CurrentUser extends User {
    private final String firstName;
    private final String lastName;
    private final String localeName;
    private final String userRole;
    public CurrentUser(String username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       String firstName,
                       String lastName,
                       String localeName,
                       String userRole) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.localeName = localeName;
        this.userRole = userRole;
    }
}
