package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.dto.LoanDto;

public interface LoanService {
    LoanDto create(LoanDto dto);
    LoanDto get(Integer uuid);
    LoanDto update(LoanDto dto);
    void delete(Integer id);
}
