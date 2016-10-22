package com.grapeup.web.security;

import com.grapeup.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class AuthTokenProcessingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthTokenProcessingFilter.class.getName());

    @Autowired
    private AuthUserProvider authUserProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("AuthToken");
        if (token != null) {
            log.debug("Token retrieved from 'AuthToken' header: {}", token);
            User user = authUserProvider.getAuthUser(token);
            if (user != null) {
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(), Collections.EMPTY_LIST);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.warn("Invalid request; authentication token missing");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isOptionRequest(HttpServletRequest request) {
        return RequestMethod.OPTIONS.toString().equals(request.getMethod());
    }
}
