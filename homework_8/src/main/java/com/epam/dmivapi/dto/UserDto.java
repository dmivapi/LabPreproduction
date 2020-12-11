package com.epam.dmivapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class UserDto implements Serializable {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String localeName;
    private Role userRole;
    private boolean blocked;
}
