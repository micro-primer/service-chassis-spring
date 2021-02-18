package com.pttrn42.microprimer.servicechassispring.infrastructure.servlet;

import brave.spring.webmvc.DelegatingTracingFilter;
import com.pttrn42.microprimer.servicechassispring.ApplicationConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    final public static String APPLICATION_ROOT = "/";

    @Override
    protected String[] getServletMappings() {
        return new String[] { APPLICATION_ROOT };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { ApplicationConfiguration.class };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new DelegatingTracingFilter() };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { DispatcherConfig.class };
    }
}
