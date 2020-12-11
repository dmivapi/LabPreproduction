package com.epam.dmivapi.service;

import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsersByRole(Role role, int currentPage, int recordsPerPage);

    int countUsersPagesByRole(Role role, int recordsPerPage);
    void createUser(UserDto userDto);
    void updateUserBlock(Integer userId, String blockOption);
    void deleteUser(Integer userId);
}
