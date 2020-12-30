package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.dto.UserDto;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto get(Integer id);
    UserDto update(UserDto dto);
    void delete(Integer id);
}
