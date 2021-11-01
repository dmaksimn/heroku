package com.example.demo.web.controller;

import com.example.demo.domain.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping
    public UserDto auth(Principal principal) {
        LOGGER.debug("User {} logged in", principal.getName());
        UserDto user = new UserDto();
        user.setUsername(principal.getName());
        return user;
    }
}
