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
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/librarian/list/readers")
    public String getAllReadersForLibrarian(
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

    @RequestMapping("/admin/list/readers")
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

    @RequestMapping("/admin/list/librarians")
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

    @RequestMapping({"/guest/enter/reader", "/admin/enter/librarian"})
    public String enterUser() {
        return Path.PAGE__ENTER_USER_INFO;
    }

    @RequestMapping("/admin/create/librarian")
    public String createLibrarian(@ModelAttribute UserDto userDto) {
        userDto.setUserRole(Role.LIBRARIAN);
        userService.createUser(userDto);
        return "redirect:" + userDto.getUserRole().getDefaultPage();
    }

    @RequestMapping("/guest/create/reader")
    public String createReader(@ModelAttribute UserDto userDto) {
        userDto.setUserRole(Role.READER);
        userService.createUser(userDto);
        return "redirect:" + userDto.getUserRole().getDefaultPage();
    }

    @RequestMapping("/admin/delete/librarian")
    public String deleteUser(@RequestParam(value = ContextParam.USER_ID_TO_PROCESS) Integer userId) {
        userService.deleteUser(userId);
        return "redirect:" + Command.LIST_USERS_LIBRARIANS.getSystemName();
    }
    @RequestMapping("/admin/update/readerblocked")
    public String updateUserBlocked(
            @RequestParam(value = ContextParam.USER_ID_TO_PROCESS) Integer userId,
            @RequestParam(value = ContextParam.BLOCK_OPTION) String blockOption
    ) {
        userService.updateUserBlock(userId, blockOption);
        return "forward:" + Command.LIST_USERS_READERS_FOR_ADMIN.getSystemName();
    }
}
