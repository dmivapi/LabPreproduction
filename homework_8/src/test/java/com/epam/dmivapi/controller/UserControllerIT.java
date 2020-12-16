package com.epam.dmivapi.controller;

import com.epam.dmivapi.AbstractBaseSpringTest;
import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.utils.TestUsersGenerator;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j
public class UserControllerIT extends AbstractBaseSpringTest {

    @Test
    @Transactional
    public void userCreationWorksThroughAllLayers() throws Exception {
        //Given
        UserDto userDto = TestUsersGenerator.generateUserDtos(1).get(0);

        mockMvc.perform(post("/admin/add/librarian")
                .with(user("3@gmail.com").password("3").roles("ADMIN"))
                .flashAttr("userDto", userDto))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/admin/list/librarians")
                .with(user("3@gmail.com").password("3").roles("ADMIN"))
                .param(ContextParam.PGN_RECORDS_PER_PAGE, "0")) // 0 - to get full list without pagination
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(ContextParam.USR_USERS))
                .andExpect(model().attribute(ContextParam.USR_USERS, hasItem(
                        allOf(
                                hasProperty("email", is(userDto.getEmail())),
                                hasProperty("password", is(userDto.getPassword())),
                                hasProperty("firstName", is(userDto.getFirstName())),
                                hasProperty("lastName", is(userDto.getLastName())),
                                hasProperty("userRole", is(userDto.getUserRole()))
                        )
                )));
    }
}