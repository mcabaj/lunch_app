package com.grapeup.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author mcabaj
 */
@EnableWebMvc
@ComponentScan(basePackages = "com.grapeup")
@Configuration
public class WebMvcConfig {
}
