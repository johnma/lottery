package com.iware.lottery.config;

/**
 * Created by johnma on 2016/11/2.
 */

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Order(1)
public class ShiroFilterInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // TODO Auto-generated method stub
        FilterRegistration.Dynamic filter = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
        filter.setInitParameter("targetFilterLifeCycle", "true");

        filter.addMappingForUrlPatterns(null, false, "/*");
    }
}
