package com.epam.dmivapi.awsdemo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data @EqualsAndHashCode(of={"id"})
@NoArgsConstructor
public class Author {
    private @Id @GeneratedValue Integer id;
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
