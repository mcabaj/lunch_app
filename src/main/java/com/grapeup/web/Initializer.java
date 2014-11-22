package com.grapeup.web;

import com.grapeup.filter.CORSFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.grapeup.configs.MongoConfig;
import com.grapeup.configs.SecurityConfig;
import com.grapeup.configs.WebMvcConfig;
import com.grapeup.configs.WebSocketConfig;

import javax.servlet.Filter;

/**
 * @author mcabaj
 */
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {WebMvcConfig.class, SecurityConfig.class, MongoConfig.class, WebSocketConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CORSFilter()};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/*"};
    }
}
