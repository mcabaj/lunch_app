/*
 * Copyright (C) Grape Software
 *
 * All rights reserved. Any use, copying, modification, distribution and selling 
 * of this software and it's documentation for any purposes without authors' written
 * permission is hereby prohibited.
 */
package com.grapeup.configs;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.grapeup.web.security.AuthController;

@Configuration
@ComponentScan(basePackageClasses = AuthController.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "authTokenProcessingFilter")
    private Filter authTokenProcessingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/login").permitAll();
        http.logout().disable();

        http.authorizeRequests().anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(authTokenProcessingFilter, BasicAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }
}
