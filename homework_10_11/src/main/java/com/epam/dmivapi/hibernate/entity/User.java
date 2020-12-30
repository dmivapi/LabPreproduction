package com.epam.dmivapi.hibernate.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
@NamedQueries({@NamedQuery(name = "UserByEmail", query = "from User u where u.email=:email")})
public class User {
        @Id
        @Column(length = 16)
        private UUID id = UUID.randomUUID();
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String localeName;
        private Role userRole;
        private boolean blocked;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        List<Loan> loans = new ArrayList<>();
}
