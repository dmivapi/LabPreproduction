package com.epam.dmivapi.security;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.model.CurrentUser;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
          log.debug("method invoked");
          CurrentUser authUser = (CurrentUser) authentication.getPrincipal();
          ContextParam.setCurrentLocale(httpServletRequest.getSession(), authUser.getLocaleName());
          handle(httpServletRequest, httpServletResponse, authUser);
          log.debug("method finished");
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            CurrentUser authUser
    ) throws IOException {

        String targetUrl = Role.valueOf(authUser.getUserRole()).getDefaultPage();
        log.debug("Going to sendRedirect to: " + Role.valueOf(authUser.getUserRole()).getDefaultPage());

        if (response.isCommitted()) {
            log.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(targetUrl);
    }
}
