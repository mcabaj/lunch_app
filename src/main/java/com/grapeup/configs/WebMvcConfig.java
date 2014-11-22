package com.grapeup.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.grapeup.resources.UserResource;
import com.grapeup.web.security.AuthController;
import com.grapeup.web.security.AuthUserProviderImpl;

/**
 * @author mcabaj
 */
@EnableWebMvc
@ComponentScan(basePackageClasses = {UserResource.class, AuthController.class})
@Configuration
public class WebMvcConfig {
}
