package com.liuc.server.api.config.interceptor;

import com.liuc.server.api.common.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jclang on 2017/5/20.
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String string;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if ("loc".equals(string)) {
            registry.addResourceHandler("/upload/**").addResourceLocations(C.DOWNLOAD_FILE_RESOURCE_LOC);
        } else if ("dev".equals(string)){
            registry.addResourceHandler("/upload/**").addResourceLocations(C.DOWNLOAD_FILE_RESOURCE_DEV);
        } else if ("prd".equals(string)){
            registry.addResourceHandler("/upload/**").addResourceLocations(C.DOWNLOAD_FILE_RESOURCE_GRAY);
        } else{
            registry.addResourceHandler("/upload/**").addResourceLocations(C.DOWNLOAD_FILE_RESOURCE_PRD);
        }
    }

}
