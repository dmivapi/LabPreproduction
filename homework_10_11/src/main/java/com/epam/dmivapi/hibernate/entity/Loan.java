package com.epam.dmivapi.hibernate.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "loan")
public class Loan {
        @Id
        @Column(length = 16)
        private UUID id = UUID.randomUUID();
        @ManyToOne
        private User user;
        @ManyToOne
        private BookCopy bookCopy;
        private LocalDate dateOut;
        private LocalDate dueDate;
        private LocalDate dateIn;
        private boolean readingRoom;
}
