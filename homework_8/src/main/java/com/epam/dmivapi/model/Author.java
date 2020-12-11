package com.epam.dmivapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    @EqualsAndHashCode.Include
    private Integer id;
    private String lastName;
    private String firstName;
}
