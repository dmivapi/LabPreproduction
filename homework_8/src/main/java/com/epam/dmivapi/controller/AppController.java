package com.epam.dmivapi.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import static com.epam.dmivapi.ContextParam.*;

@Controller
@RequestMapping("/app")
@Log4j
public class AppController {
    @RequestMapping("/switchLocale")
    public String switchLanguage(
            @RequestParam(value = com.epam.dmivapi.ContextParam.CURRENT_LOCALE) String locale,
            @RequestParam(com.epam.dmivapi.ContextParam.SELF_COMMAND) String senderPage,
            HttpSession session
    ) {
        log.debug("/app/switchLocale invoked");
        setCurrentLocale(session, locale);
        return "forward:" + senderPage;
    }

    @RequestMapping("/login") // avoiding Spring Security behaviour when home and login are the same page
    public String goHomeAsLogin() {
        log.debug("/app/login invoked");
        return "forward:" + "/book/list";
    }
}
