package com.epam.dmivapi.utils;

import com.epam.dmivapi.converter.UserDtoConverter;
import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.model.User;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

@UtilityClass
public class TestUsersGenerator {
    private final UserDtoConverter userDtoConverter = new UserDtoConverter();

    public List<UserDto> generateUserDtos(int count) {
        return generateUsers(count).stream()
                .map(userDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public List<User> generateUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestUsersGenerator::createUser)
                .collect(Collectors.toList());
    }

    User createUser(int seed) {
        String token = RandomStringUtils.randomAlphabetic(abs(seed) % 10 + 1);
        return User.builder()
                .id(seed)
                .email(token + "@" + token + ".com")
                .password(token + "password")
                .firstName(token + "fname")
                .lastName(token + "lname")
                .localeName(seed % 2 == 0 ? "ru" : "en")
                .userRole(Role.values()[abs(seed) % Role.values().length])
                .blocked(seed % 2 == 0)
                .build();
    }
}
