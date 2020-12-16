package com.epam.dmivapi.repository;

import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findUsersByRole(Role role, int currentPage, int recordsPerPage);
    User findUserByEmail(String email);

    int countUsersByRole(Role role);
    void createUser(User user);
    void deleteUserById(int userId);
    void updateUserBlockById(int userId, boolean blockOption);
}
