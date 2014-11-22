package com.grapeup.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.grapeup.configs.MongoConfig;
import com.grapeup.configs.SecurityConfig;
import com.grapeup.configs.WebMvcConfig;

/**
 * @author mcabaj
 */
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {WebMvcConfig.class, SecurityConfig.class, MongoConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[0];
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/restapi/*"};
  }
}
