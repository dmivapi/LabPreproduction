package com.epam.dmivapi.hibernate.repository;

import com.epam.dmivapi.hibernate.entity.User;

public interface UserRepository {
    void save(User user);
    User get(Integer userId);
    User get(String email);
    void delete(Integer userId);
}
