package com.epam.dmivapi.controller;

import com.epam.dmivapi.AbstractBaseTestCase;
import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.service.UserService;
import com.epam.dmivapi.utils.TestUsersGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class UserControllerTest extends AbstractBaseTestCase {
    @Mock
    private UserService userService;

    @InjectMocks
    UserController sut;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    void getAllReadersForLibrarian() throws Exception {
        //Given
        final int USER_LIST_SIZE = 9;
        final int RECORDS_PER_PAGE = 0; // no pagination
        final int NUMBER_OF_PAGES = 9;

        List<UserDto> usersDto = TestUsersGenerator.generateUserDtos(USER_LIST_SIZE);

        doReturn(usersDto)
                .when(userService)
                .getUsersByRole(any(), anyInt(), anyInt());

        doReturn(NUMBER_OF_PAGES)
                .when(userService)
                .countUsersPagesByRole(any(), anyInt());

        //When
        mockMvc.perform(post("/librarian/list/readers")
                .param(ContextParam.PGN_RECORDS_PER_PAGE, String.valueOf(RECORDS_PER_PAGE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(Path.PAGE__LIST_USERS_READERS_FOR_LIBRARIAN))
                .andExpect(forwardedUrl(Path.PAGE__LIST_USERS_READERS_FOR_LIBRARIAN))
                .andExpect(model().attribute(ContextParam.USR_USERS, hasSize(USER_LIST_SIZE)))
                .andExpect(model().attribute(ContextParam.USR_USERS, containsInRelativeOrder(usersDto.toArray())))
                .andExpect(model().attribute(ContextParam.PGN_RECORDS_PER_PAGE, RECORDS_PER_PAGE))
                .andExpect(model().attribute(ContextParam.PGN_NUMBER_OF_PAGES, NUMBER_OF_PAGES)
                );

        //Then
        verify(userService, times(1)).getUsersByRole(
                any(), anyInt(), anyInt());
        verify(userService, times(1)).countUsersPagesByRole(
                any(), anyInt());
    }
}