package com.epam.dmivapi.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Publisher {
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
}
