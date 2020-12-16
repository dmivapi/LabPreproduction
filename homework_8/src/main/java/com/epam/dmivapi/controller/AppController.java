package com.epam.dmivapi.controller;

import com.epam.dmivapi.config.LocaleConfig;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
@Log4j
public class AppController {
    @Autowired
    LocaleConfig localeConfig;

    @RequestMapping("/switchLocale")
    public String switchLanguage(
            @RequestParam(value = com.epam.dmivapi.ContextParam.CURRENT_LOCALE) String locale,
            @RequestParam(com.epam.dmivapi.ContextParam.SELF_COMMAND) String senderPage,
            HttpSession session
    ) {
        log.debug("/app/switchLocale invoked");
        localeConfig.setCurrentLocale(session, locale);
        return "forward:" + senderPage;
    }

    @RequestMapping("/login") // avoiding Spring Security behaviour when home and login are the same page
    public String goHomeAsLogin() {
        log.debug("/app/login invoked");
        return "forward:" + "/book/list";
    }
}
