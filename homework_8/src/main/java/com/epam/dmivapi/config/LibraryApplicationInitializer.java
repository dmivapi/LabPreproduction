package com.epam.dmivapi.config;

import com.epam.dmivapi.ContextParam;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.PropertyConfigurator;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.jsp.jstl.core.Config;

@Log4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class LibraryApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebSecurityConfig.class, PersistenceConfig.class};
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

        try {
            String log4jConfigFile = servletContext.getInitParameter(ContextParam.LOG4J_CONFIG_LOCATION);
            String fullPath = servletContext.getRealPath("") + log4jConfigFile;

            PropertyConfigurator.configure(fullPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // list of available locales from web.xml
        ContextParam.setLocalesList(servletContext,
                servletContext.getInitParameter(ContextParam.LOCALES)
        );

        // default locale from web.xml
        ContextParam.setDefaultLocale(servletContext,
                servletContext.getInitParameter(Config.FMT_LOCALE)
        );

        // UTF8 Character Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);

        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }
}
