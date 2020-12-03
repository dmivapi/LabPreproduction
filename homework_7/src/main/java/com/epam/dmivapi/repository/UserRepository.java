package com.epam.dmivapi.repository;

import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findUsersByRole(Role role, int currentPage, int recordsPerPage);

    int countUsersByRole(Role role);

    User findUserByEmail(String email);
}
