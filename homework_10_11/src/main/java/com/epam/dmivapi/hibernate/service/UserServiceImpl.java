package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.assembler.UserAssembler;
import com.epam.dmivapi.hibernate.dto.UserDto;
import com.epam.dmivapi.hibernate.entity.Loan;
import com.epam.dmivapi.hibernate.entity.User;
import com.epam.dmivapi.hibernate.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    @Transactional(readOnly = true)
    public UserDto get(String email) {
        User user = userRepository.get(email);
        return userAssembler.assemble(user);
    }

    @Override
    @Transactional
    public UserDto update(UserDto dto) {
        User entity = userRepository.get(dto.getId());
        User updatedEntity = userAssembler.assemble(dto);
        performUpdate(entity, updatedEntity);
        return userAssembler.assemble(entity);
    }

    private void performUpdate(User persistentEntity, User newEntity) {
        persistentEntity.setEmail(newEntity.getEmail());
        persistentEntity.setPassword(newEntity.getPassword());
        persistentEntity.setFirstName(newEntity.getFirstName());
        persistentEntity.setLastName(newEntity.getLastName());
        persistentEntity.setLocaleName(newEntity.getLocaleName());
        persistentEntity.setUserRole(newEntity.getUserRole());
        persistentEntity.setBlocked(newEntity.isBlocked());
        updateLoans(persistentEntity.getLoans(), newEntity.getLoans());
    }

    private void updateLoans(List<Loan> persistentLoans, List<Loan> newLoans) {
        //persistentLoans.stream()

    }

    @Override
    @Transactional
    public void delete(UUID id) {
        userRepository.delete(id);
    }
}
