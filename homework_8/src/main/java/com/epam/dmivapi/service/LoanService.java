package com.epam.dmivapi.service;

import com.epam.dmivapi.dto.LoanDto;
import com.epam.dmivapi.dto.LoanDtoViewAll;

import java.util.List;

public interface LoanService {
    List<LoanDtoViewAll> getAllLoan(
            String genreLanguageCode,
            int currentPage,
            int recordsPerPage
    );

    List<LoanDto> getLoansByUserId(
            Integer userId,
            String genreLanguageCode,
            int currentPage,
            int recordsPerPage
    );

    int countLoanPages(
            String genreLanguageCode,
            int recordsPerPage
    );

    int countLoanPagesByUserId(
            Integer userId,
            String genreLanguageCode,
            int recordsPerPage
    );

    void createLoansByUserIdAndPublicationsList(Integer userId, List<Integer> publicationIds);

    void updateLoanStatusToOutById(Integer loanId);

    void updateLoanStatusToReturnedById(Integer loanId);

    void deleteLoanById(Integer loanId);
}
