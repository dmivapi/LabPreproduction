package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.dto.LoanDto;

import java.util.UUID;

public interface LoanService {
    LoanDto create(LoanDto dto);
    LoanDto get(UUID uuid);
    LoanDto update(LoanDto dto);
    void delete(UUID id);
}
