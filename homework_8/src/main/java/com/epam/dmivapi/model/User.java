package com.epam.dmivapi.model;

import com.epam.dmivapi.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String localeName;
    private Role userRole;
    private boolean blocked;
}
