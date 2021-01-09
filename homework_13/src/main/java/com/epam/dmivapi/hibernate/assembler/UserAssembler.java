package com.epam.dmivapi.hibernate.assembler;

import com.epam.dmivapi.hibernate.dto.UserDto;
import com.epam.dmivapi.hibernate.entity.User;

public interface UserAssembler {
    User assemble(UserDto userDto);
    UserDto assemble(User user);
}
