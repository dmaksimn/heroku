package com.example.demo.web;

import com.example.demo.domain.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Entry point for basic configuration
 * It's customizes error response on authentication failure
 */
@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationEntryPoint.class);
    private final ObjectMapper mapper;

    public CustomBasicAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter writer = response.getWriter();
        ErrorResponse error = new ErrorResponse("Cannot find user with this name and password", 403);
        LOGGER.info("Cannot authenticate user. Reason: [{}]", authEx.getMessage());
        writer.println(mapper.writeValueAsString(error));
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("Demo");
        super.afterPropertiesSet();
    }
}
