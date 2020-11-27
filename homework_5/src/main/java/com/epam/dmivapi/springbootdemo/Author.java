package com.epam.dmivapi.springbootdemo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data @EqualsAndHashCode(of={"id"})
public class Author {
    private @Id @GeneratedValue Integer id;
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
