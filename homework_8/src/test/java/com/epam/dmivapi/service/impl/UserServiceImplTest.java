package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.converter.UserDtoConverter;
import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.model.User;
import com.epam.dmivapi.repository.UserRepository;
import com.epam.dmivapi.utils.TestUsersGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {
    private UserRepository userRepository = mock(UserRepository.class);
    private final UserDtoConverter userDtoConverter = new UserDtoConverter();
    private final UserServiceImpl sut = new UserServiceImpl(userRepository, userDtoConverter);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUsersByRole() {
        //Given
        final int NUM_OF_USERS = 11;
        List<User> users = TestUsersGenerator.generateUsers(NUM_OF_USERS);
        doReturn(users)
                .when(userRepository).findUsersByRole(any(), anyInt(), anyInt());

        //When
        List<UserDto> foundUsers = sut.getUsersByRole(Role.ADMIN, 1, 50);

        //Then
        verify(userRepository).findUsersByRole(any(), anyInt(), anyInt());
        assertThat(foundUsers, hasSize(NUM_OF_USERS));

        users.forEach(user -> assertThat(foundUsers, hasItem(allOf(
                hasProperty("id", is(user.getId())),
                hasProperty("email", is(user.getEmail())),
                hasProperty("password", is(user.getPassword())),
                hasProperty("firstName", is(user.getFirstName())),
                hasProperty("lastName", is(user.getLastName())),
                hasProperty("userRole", is(user.getUserRole()))
        ))));
    }
}