package com.grapeup.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan(basePackages={"com.grapeup.resources", "com.grapeup.websocket"})
@Configuration
public class WebMvcConfig {
}
