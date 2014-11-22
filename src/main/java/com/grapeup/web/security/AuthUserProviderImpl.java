/*
 * Copyright (C) Grape Software
 *
 * All rights reserved. Any use, copying, modification, distribution and selling 
 * of this software and it's documentation for any purposes without authors' written
 * permission is hereby prohibited.
 */
package com.grapeup.web.security;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
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

    private static final int TOKEN_EXPIRATION_MILLIS = 1000 * 60 * 60; // 60 min

    // username -> token map
    private ConcurrentMap<String, String> tokens = new ConcurrentHashMap<>();
    // token -> user map
    private ConcurrentMap<String, AuthUserData> authUsers = new ConcurrentHashMap<>();

    @Override
    public String generateToken(User user) {
        String username = user.getUsername();
        String token = DigestUtils.sha1Hex(username + RandomStringUtils.random(20));
        Date expirationDate = createNewExpirationDate();
        log.debug("Generated auth token {} for user {}", token, username);

        AuthUserData authUserData = new AuthUserData(token, expirationDate, user);
        String oldToken = tokens.get(username);
        tokens.put(username, token);
        if (oldToken != null) {
            authUsers.remove(oldToken);
        }
        authUsers.put(token, authUserData);

        return token;
    }

    @Override
    public User getAuthUser(String token) {
        AuthUserData authUserData;
        User user = null;
        if (token != null) {
            authUserData = authUsers.get(token);
            if (authUserData != null) {
                Date expirationDate = authUserData.getExpirationDate();
                Date now = new Date();
                if (expirationDate.before(now)) {
                    log.debug("Token {} is invalid; token expired", token);
                    removeAuthUser(authUserData.getUser().getUsername());
                } else {
                    user = authUserData.getUser();
                    authUserData.setExpirationDate(createNewExpirationDate());
                    log.debug("Token {} is valid; authenticated user: {}", token, user.getUsername());
                }
            } else {
                log.debug("Token {} is invalid; no authenticated user found", token);
            }
        }
        return user;
    }

    @Override
    public void removeAuthUser(String username) {
        if (username != null) {
            String token = tokens.remove(username);
            if (token != null) {
                log.debug("Removing user '{}' from authenticated users map", username);
                authUsers.remove(token);
            }
        }
    }

    private Date createNewExpirationDate() {
        return DateUtils.addMilliseconds(new Date(), TOKEN_EXPIRATION_MILLIS);
    }

    private class AuthUserData {
        private String token;
        private Date expirationDate;
        private User user;

        private AuthUserData(String token, Date expirationDate, User user) {
            this.token = token;
            this.expirationDate = expirationDate;
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Date getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
