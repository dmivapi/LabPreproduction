package com.epam.dmivapi.dto;

import com.epam.dmivapi.model.Loan;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter
public class LoanDtoViewAll implements Serializable {
    private Integer id;
    private LocalDate dateOut;
    private LocalDate dueDate;
    private LocalDate dateIn;
    private boolean readingRoom;
    private LoanStatus status;

    private String libCode; // code for in-library referencing
    private String bookTitle;
    private String bookAuthors;
    private String bookGenre;
    private String bookPublisher;
    private int bookPublicationYear;
    private int price;

    private int userId;
    private String email; // login
    private String firstName;
    private String lastName;
    private boolean blocked;
}
