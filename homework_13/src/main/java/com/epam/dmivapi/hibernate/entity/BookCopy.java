package com.epam.dmivapi.hibernate.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "book_copy")
public class BookCopy {
        @Id
        @Column(length = 16)
        @GeneratedValue
        private UUID id;
        private String title;
        private String authors;
        private String genre;
        private String publisher;
        private int year;
        private int price;
}
