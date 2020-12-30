package com.epam.dmivapi.hibernate.dto;

import com.epam.dmivapi.hibernate.entity.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String localeName;
    private Role userRole;
    private boolean blocked;

    List<LoanDto> loans = new ArrayList<>();
}
