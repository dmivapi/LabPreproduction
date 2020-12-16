package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.converter.UserDtoConverter;
import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.exception.EntityAlreadyExistsException;
import com.epam.dmivapi.model.User;
import com.epam.dmivapi.repository.UserRepository;
import com.epam.dmivapi.repository.impl.UserRepositoryImpl;
import com.epam.dmivapi.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoConverter userDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public List<UserDto> getUsersByRole(Role role, int currentPage, int recordsPerPage) {
        log.debug("method invoked");
        List<User> users = userRepository.findUsersByRole(role, currentPage, recordsPerPage);
        return userDtoConverter.convertList(users);
    }

    @Override
    public int countUsersPagesByRole(Role role, int recordsPerPage) {
        log.debug("method invoked");
        return ServiceUtils.calculateNumOfPages(
                userRepository.countUsersByRole(role),
                recordsPerPage
        );
    }

    @Override
    public void deleteUser(Integer userId) {
        log.debug("method invoked");
        userRepository.deleteUserById(userId);
    }

    @Override
    public void createUser(UserDto userDto) {
        log.debug("method invoked");

        // check if this user already exists
        User userFromDB = userRepository.findUserByEmail(userDto.getEmail());

        if (userFromDB != null)
            throw new EntityAlreadyExistsException("This user is already registered, please login instead");

        userRepository.createUser(userDtoConverter.convert(userDto));
    }

    @Override
    public void updateUserBlock(Integer userId, String blockOption) {
        log.debug("method invoked");
        userRepository.updateUserBlockById(userId, Boolean.parseBoolean(blockOption));
    }
}
