package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto get(String email);
    UserDto update(UserDto dto);
    void delete(UUID id);
}
