package com.epam.dmivapi.model;

import com.epam.dmivapi.dto.Role;
import lombok.*;

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

    public Integer getId() { //TODO: check out if needed for Nullable and remove
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
