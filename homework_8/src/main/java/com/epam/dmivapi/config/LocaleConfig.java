package com.epam.dmivapi.config;

import com.epam.dmivapi.ContextParam;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

@Configuration
@Log4j
public class LocaleConfig {
    @Value("${library.defaultLocale:en}")
    private String defaultLocale;

    @Value("${library.locales:en,ru}")
    private List<String> locales;

    @Autowired
    LocaleResolver localeResolver;

    public List<String> getLocalesList(HttpSession session) {
        return locales;
    }

    public void setLocalesList(ServletContext servletContext) {
        servletContext.setAttribute(ContextParam.LOCALES, locales);
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(ServletContext servletContext) {
        servletContext.setAttribute(ContextParam.DEFAULT_LOCALE, defaultLocale);
    }

    public String getCurrentLocale(HttpSession session) {
        String locale = (String) session.getAttribute(ContextParam.CURRENT_LOCALE);
        return locale == null ? getDefaultLocale() : locale;
    }

    public void setCurrentLocale(HttpSession session, String newLocale) {
        // if newLocale is not valid - keep current locale
        if (newLocale == null || !getLocalesList(session).contains(newLocale)) {
            log.warn("Session attribute was not set: CURRENT_LOCALE --> " + newLocale);
            return;
        }
        session.setAttribute(ContextParam.CURRENT_LOCALE, newLocale);
        Config.set(session, Config.FMT_LOCALE, newLocale); // for jsp pages i18n
        log.trace("Set the session attribute: CURRENT_LOCALE --> " + newLocale);
    }

    @Bean
    public HandlerInterceptor libLocaleInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String language = getCurrentLocale(request.getSession());
                if (language == null) {
                    language = getDefaultLocale();
                    setCurrentLocale(request.getSession(), language);
                }
                ((SessionLocaleResolver) localeResolver).setLocale(request, response, new Locale(language));
                return true;
            }
        };
    }
}
