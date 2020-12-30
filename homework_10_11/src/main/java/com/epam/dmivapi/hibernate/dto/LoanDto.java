package com.epam.dmivapi.hibernate.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class LoanDto {
    private UUID id;
    private UUID bookCopyId;
    private LocalDate dateOut;
    private LocalDate dueDate;
    private LocalDate dateIn;
    private boolean readingRoom;
}
