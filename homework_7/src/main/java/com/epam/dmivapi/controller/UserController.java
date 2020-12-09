package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.service.UserService;
import com.epam.dmivapi.dto.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/librarian/borrowers")
    public String getAllBorrowersForLibrarian(
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        List<UserDto> users = userService.getUsersByRole(
                Role.READER,
                currentPage,
                recordsPerPage
        );
        model.addAttribute(ContextParam.USR_USERS, users);

        int nOfPages = userService.countUsersPagesByRole(
                Role.READER,
                recordsPerPage
        );

        model.addAttribute(ContextParam.PGN_RECORDS_PER_PAGE, recordsPerPage);
        model.addAttribute(ContextParam.PGN_NUMBER_OF_PAGES, nOfPages);

        return Path.PAGE__LIST_USERS_READERS_FOR_LIBRARIAN;
    }

    @RequestMapping("/admin/borrowers")
    public String getAllBorrowersForAdmin(
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        List<UserDto> users = userService.getUsersByRole(
                Role.READER,
                currentPage,
                recordsPerPage
        );
        model.addAttribute(ContextParam.USR_USERS, users);

        int nOfPages = userService.countUsersPagesByRole(
                Role.READER,
                recordsPerPage
        );

        model.addAttribute(ContextParam.PGN_RECORDS_PER_PAGE, recordsPerPage);
        model.addAttribute(ContextParam.PGN_NUMBER_OF_PAGES, nOfPages);

        return Path.PAGE__LIST_USERS_READERS_FOR_ADMIN;
    }

    @RequestMapping("/admin/librarians")
    public String getAllLibrarians(
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        List<UserDto> users = userService.getUsersByRole(
                Role.LIBRARIAN,
                currentPage,
                recordsPerPage
        );
        model.addAttribute(ContextParam.USR_USERS, users);

        int nOfPages = userService.countUsersPagesByRole(
                Role.LIBRARIAN,
                recordsPerPage
        );

        model.addAttribute(ContextParam.PGN_RECORDS_PER_PAGE, recordsPerPage);
        model.addAttribute(ContextParam.PGN_NUMBER_OF_PAGES, nOfPages);

        return Path.PAGE__LIST_USERS_LIBRARIANS;
    }

    @RequestMapping("/enter")
    public String enterUser() {
        return Path.PAGE__ENTER_USER_INFO;
    }

    @RequestMapping("/create/librarian")
    public String createUserLibrarian(@ModelAttribute UserDto userDto) {
        userDto.setUserRole(Role.LIBRARIAN);
        userService.createUser(userDto);
        return userDto.getUserRole().getDefaultPage();
    }

    @RequestMapping("/create/reader")
    public String createUserReader(@ModelAttribute UserDto userDto) {
        userDto.setUserRole(Role.READER);
        userService.createUser(userDto);
        return userDto.getUserRole().getDefaultPage();
    }

    @RequestMapping("/delete")
    public String deleteUser(@RequestParam(value = ContextParam.USER_ID_TO_PROCESS) Integer userId) {
        userService.deleteUser(userId);
        return Path.PAGE__ENTER_USER_INFO;
    }
    @RequestMapping("/updateblock")
    public String updateUserBlock(
            @RequestParam(value = ContextParam.USER_ID_TO_PROCESS) Integer userId,
            @RequestParam(value = ContextParam.BLOCK_OPTION) String blockOption
    ) {
        userService.updateUserBlock(userId, blockOption);
        return Path.PAGE__ENTER_USER_INFO;
    }
}
