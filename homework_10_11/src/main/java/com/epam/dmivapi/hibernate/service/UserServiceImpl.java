package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.assembler.UserAssembler;
import com.epam.dmivapi.hibernate.dto.UserDto;
import com.epam.dmivapi.hibernate.entity.User;
import com.epam.dmivapi.hibernate.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserAssembler userAssembler;

    public UserServiceImpl(UserRepository userRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    @Override
    public UserDto create(UserDto dto) {
        User user = userAssembler.assemble(dto);
        userRepository.save(user);
        return userAssembler.assemble(user);
    }

    @Override
    public UserDto get(Integer id) {
        return null;
    }

    @Override
    public UserDto update(UserDto dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
