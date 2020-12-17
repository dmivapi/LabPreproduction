package com.epam.dmivapi.utils;

import com.epam.dmivapi.converter.LoanDtoConverter;
import com.epam.dmivapi.dto.LoanDto;
import com.epam.dmivapi.model.Loan;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

@UtilityClass
public class TestLoansGenerator {
    private final LoanDtoConverter loanDtoConverter = new LoanDtoConverter();

    public List<LoanDto> generateLoanDtos(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestLoansGenerator::createLoan)
                .map(loanDtoConverter::convert)
                .collect(Collectors.toList());
    }

    Loan createLoan(int seed) {
        String token = RandomStringUtils.randomAlphabetic(abs(seed) % 10 + 1);
        int LOAN_TERM = 15;
        return Loan.builder()
                .id(seed)
                .dateOut(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(LOAN_TERM))
                .dateIn(LocalDate.now().plusDays(LOAN_TERM / 2))
                .readingRoom(seed % 2 == 0)
                .build();
    }
}
