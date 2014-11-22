/*
 * Copyright (C) Grape Software
 *
 * All rights reserved. Any use, copying, modification, distribution and selling 
 * of this software and it's documentation for any purposes without authors' written
 * permission is hereby prohibited.
 */
package com.grapeup.web.security;

import com.grapeup.domain.User;

/**
 * @author rosw
 */
public interface AuthUserProvider {

    public String generateToken(User user);

    public User getAuthUser(String token);

    public void removeAuthUser(String username);
}
