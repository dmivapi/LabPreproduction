package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.config.LocaleConfig;
import com.epam.dmivapi.dto.LoanDto;
import com.epam.dmivapi.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.epam.dmivapi.utils.TestLoansGenerator.generateLoanDtos;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

;

class LoanControllerTest {
    @Mock
    private LoanService loanService;

    @Mock
    private LocaleConfig localeConfig;

    @Mock
    private MockHttpSession session;

    @InjectMocks
    private LoanController sut;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    void getLoansByUserId() throws Exception {
        final int LOAN_LIST_SIZE = 9;
        final int NUMBER_OF_PAGES = 1;
        final String USER_ID = "17";

        //Given
        List<LoanDto> loans = generateLoanDtos(LOAN_LIST_SIZE);
        doReturn("ru")
                .when(localeConfig)
                .getCurrentLocale(any());

        doReturn(loans)
                .when(loanService)
                .getLoansByUserId(any(), anyString(), anyInt(), anyInt());

        doReturn(NUMBER_OF_PAGES)
                .when(loanService)
                .countLoanPagesByUserId(any(), anyString(), anyInt());

        //When
        mockMvc.perform(post("/loan/" + USER_ID)
                .param(ContextParam.PGN_RECORDS_PER_PAGE, String.valueOf(LOAN_LIST_SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(Path.PAGE__LIST_LOANS_SINGLE_USER))
                .andExpect(model().attribute(ContextParam.LS_LOANS, hasSize(LOAN_LIST_SIZE)))
                .andExpect(model().attribute(ContextParam.LS_LOANS, containsInRelativeOrder(loans.toArray())))
                .andExpect(model().attribute(ContextParam.USER_ID_TO_PROCESS, Integer.parseInt(USER_ID)))
                .andExpect(model().attribute(ContextParam.PGN_RECORDS_PER_PAGE, LOAN_LIST_SIZE))
                .andExpect(model().attribute(ContextParam.PGN_NUMBER_OF_PAGES, NUMBER_OF_PAGES)
                );

        //Then
        verify(loanService, times(1)).getLoansByUserId(any(), anyString(), anyInt(), anyInt());
        verify(loanService, times(1)).countLoanPagesByUserId(any(), anyString(), anyInt());
    }
}