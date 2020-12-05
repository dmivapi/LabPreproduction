package com.epam.dmivapi.config;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.service.impl.UserDetailsServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.jsp.jstl.core.Config;

@Log4j
public class LibraryApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        // TODO: set it up to be read from web.xml
        servletContext.setInitParameter(ContextParam.DEFAULT_LOAN_TERM_IN_DAYS,"30");
        servletContext.setInitParameter(ContextParam.LOG4J_CONFIG_LOCATION,"/WEB-INF/log4j.properties");
        servletContext.setInitParameter(Config.FMT_LOCALIZATION_CONTEXT, "resources");
        servletContext.setInitParameter(Config.FMT_LOCALE, "en");
        servletContext.setInitParameter(ContextParam.LOCALES, "ru en");

        // UTF8 Character Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);

        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }
}
