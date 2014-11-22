/*
 * Copyright (C) Grape Software
 *
 * All rights reserved. Any use, copying, modification, distribution and selling 
 * of this software and it's documentation for any purposes without authors' written
 * permission is hereby prohibited.
 */
package com.grapeup.web.security;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grapeup.domain.User;
import com.grapeup.dto.CredentialsDto;
import com.grapeup.repositories.UserRepository;

/**
 * @author rosw
 */
@RestController
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthUserProvider authUserProvider;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/plain")
    @ResponseBody
    public String login(@RequestBody CredentialsDto credentials, HttpServletResponse response) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        User user = new User();//userRepository.findByUsernameAndPassword(username, password);
        user.setUsername("grape");
        user.setPassword("grape");
        //if (user == null) {
        // TODO
        if (username.equals("grape") && password.equals("grape")) {
            log.debug("User {} found; generating auth token");
            String token = authUserProvider.generateToken(user);
            response.setStatus(HttpStatus.OK.value());
            return token;
        } else {
            log.warn("User {} not found; username or password is invalid", username);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return "";
        }
    }
}
