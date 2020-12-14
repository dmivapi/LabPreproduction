package com.epam.dmivapi.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @EqualsAndHashCode.Include
    private Integer id;
    private String title;
    private String authors;
    private String genre;
    private String publisher;
    private int year;
    private int price;
}
