package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.dto.UserDto;
import com.epam.dmivapi.service.UserService;
import com.epam.dmivapi.dto.Role;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@Log4j
public class UserController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private AuthenticationSuccessHandler successHandler;

    @RequestMapping("/librarian/list/readers")
    public String getAllReadersForLibrarian(
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        log.debug("/librarian/list/readers invoked");
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
    public String getAllReadersForAdmin(
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        log.debug("/admin/list/readers invoked");
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
        log.debug("/admin/list/librarians invoked");
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
        log.debug("/*/enter/reader invoked");
        return Path.PAGE__ENTER_USER_INFO;
    }

    @RequestMapping("/admin/create/librarian")
    public String createLibrarian(@ModelAttribute UserDto userDto) {
        log.debug("/admin/create/librarian invoked");
        userDto.setUserRole(Role.LIBRARIAN);
        userService.createUser(userDto);
        return "redirect:" + Command.LIST_USERS_LIBRARIANS.getSystemName();
    }

    @RequestMapping("/guest/create/reader")
    public String createReader(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        log.debug("/guest/create/reader invoked");
        userDto.setUserRole(Role.READER);
        userService.createUser(userDto);
        return "forward:" + "/guest/dologin";
    }

    @RequestMapping("/admin/delete/librarian")
    public String deleteLibrarian(
            @RequestParam(value = ContextParam.USER_ID_TO_PROCESS) Integer userId,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage
    ) {
        log.debug(" invoked");
        userService.deleteUser(userId);
        return "forward:" + senderPage;
    }

    @RequestMapping("/admin/update/readerblocked")
    public String updateUserBlocked(
            @RequestParam(value = ContextParam.USER_ID_TO_PROCESS) Integer userId,
            @RequestParam(value = ContextParam.BLOCK_OPTION) String blockOption,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage
    ) {
        log.debug("/admin/update/readerblocked invoked");
        userService.updateUserBlock(userId, blockOption);
        return "forward:" + senderPage;
    }

    @RequestMapping("/guest/doLogin")
    private String doLogin(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(HtmlUtils.htmlEscape(userDto.getEmail()), userDto.getPassword());
        log.debug("/guest/doLogin invoked");
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        try {
            successHandler.onAuthenticationSuccess(request, response, auth);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
        return "redirect:" + userDto.getUserRole().getDefaultPage();
    }
}
