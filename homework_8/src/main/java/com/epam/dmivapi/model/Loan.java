package com.epam.dmivapi.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Loan {
    @EqualsAndHashCode.Include
    private Integer id;
    private Integer userId;
    private Integer bookCopyId;
    private LocalDate dateOut;
    private LocalDate dueDate;
    private LocalDate dateIn;
    private boolean readingRoom;

    private String libCode; // code for in-library referencing
    private String bookTitle;
    private String bookAuthors;
    private String bookGenre;
    private String bookPublisher;
    private int bookPublicationYear;
    private int price;

    private String email; // login
    private String firstName;
    private String lastName;
    private boolean blocked;
}
