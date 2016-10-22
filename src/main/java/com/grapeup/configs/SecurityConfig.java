package com.grapeup.configs;

import javax.annotation.Resource;
import javax.servlet.Filter;

import com.grapeup.filter.CORSFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.grapeup.web.security.AuthController;

@Configuration
@ComponentScan(basePackageClasses = AuthController.class, basePackages = "com.grapeup.filter")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "authTokenProcessingFilter")
    private Filter authTokenProcessingFilter;
    @Resource(name = "CORSFilter")
    private Filter CORSFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();


        http.logout().disable();
        http.addFilterBefore(CORSFilter, ChannelProcessingFilter.class);
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/websocket").permitAll();

        http.authorizeRequests().anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(authTokenProcessingFilter, BasicAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }
}
