package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


class LoanServiceImplTest {
    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void countLoansByUserId() {
        int NUM_OF_LOANS = 23;

        //Given
        doReturn(NUM_OF_LOANS)
                .when(loanRepository).countLoansByUserId(anyInt(), anyString());

        //When
        int numOfPages = sut.countLoanPagesByUserId(1, "ru", 1);

        //Then
        verify(loanRepository).countLoansByUserId(anyInt(), anyString());
        assertEquals(NUM_OF_LOANS, numOfPages);
    }
}