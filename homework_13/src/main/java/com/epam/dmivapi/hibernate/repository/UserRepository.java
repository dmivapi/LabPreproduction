package com.epam.dmivapi.hibernate.repository;

import com.epam.dmivapi.hibernate.entity.User;

import java.util.UUID;

public interface UserRepository {
    void save(User user);
    User get(UUID userId);
    User get(String email);
    void delete(UUID userId);
}
