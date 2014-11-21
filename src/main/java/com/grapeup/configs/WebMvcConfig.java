package com.grapeup.configs;

import com.grapeup.resources.UserResource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author mcabaj
 */
@EnableWebMvc
@ComponentScan(basePackageClasses = UserResource.class)
@Configuration
public class WebMvcConfig {
}