package com.epam.dmivapi.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre {
    @EqualsAndHashCode.Include
    private Integer id;
    private int bookId;
    private int languageId;
    private int genreId;
    private String name;
}
