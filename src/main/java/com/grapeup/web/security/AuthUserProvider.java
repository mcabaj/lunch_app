package com.grapeup.web.security;

import com.grapeup.domain.User;

public interface AuthUserProvider {

    String generateToken(User user);

    User getAuthUser(String token);

    void removeAuthUser(String username);
}
