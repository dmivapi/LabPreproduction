package com.epam.dmivapi.security;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.dto.Role;
import com.epam.dmivapi.model.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
          CurrentUser authUser = (CurrentUser) authentication.getPrincipal();
          ContextParam.setCurrentLocale(httpServletRequest.getSession(), authUser.getLocaleName());
          httpServletResponse.setStatus(HttpServletResponse.SC_OK);
          httpServletResponse.sendRedirect(Role.valueOf(authUser.getUserRole()).getDefaultPage());
    }
}
