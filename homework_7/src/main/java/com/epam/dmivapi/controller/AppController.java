package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.dmivapi.ContextParam.setCurrentLocale;

@Controller
@Log4j
@RequestMapping("/app")
public class AppController {
    @RequestMapping("/switchLocale")
    public String switchLanguage(
            @RequestParam(value = ContextParam.CURRENT_LOCALE) String locale,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage,
            HttpSession session
    ) {
        log.debug("SwitchLocaleCmd starts");
        setCurrentLocale(session, locale);
        log.debug("SwitchLocaleCmd finished");

        return "forward:" + senderPage;
    }
}
