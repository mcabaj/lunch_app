/*
 * Copyright (C) Grape Software
 *
 * All rights reserved. Any use, copying, modification, distribution and selling 
 * of this software and it's documentation for any purposes without authors' written
 * permission is hereby prohibited.
 */
package com.grapeup.web.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.grapeup.domain.User;

/**
 * @author rosw
 */
@Component
public class AuthUserProviderImpl implements AuthUserProvider {

    private static final Logger log = LoggerFactory.getLogger(AuthUserProviderImpl.class.getName());

    private ConcurrentMap<String, String> tokens = new ConcurrentHashMap<>();
    private ConcurrentMap<String, User> authUsers = new ConcurrentHashMap<>();

    @Override
    public String generateToken(User user) {
        String username = user.getUsername();
        String token = DigestUtils.sha1Hex(username + RandomStringUtils.random(20));
        log.debug("Generated auth token {} for user {}", token, username);

        String oldToken = tokens.replace(username, token);
        authUsers.remove(oldToken);
        authUsers.put(token, user);

        return token;
    }

    @Override
    public User getAuthUser(String token) {
        User user = authUsers.get(token);
        if (user != null) {
            log.debug("Token {} is valid; authenticated user: {}", token, user.getUsername());
        } else {
            log.debug("Token {} is invalid; no authenticated user found");
        }
        return user;
    }
}
