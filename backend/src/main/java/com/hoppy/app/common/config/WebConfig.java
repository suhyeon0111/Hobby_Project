package com.hoppy.app.common.config;

import com.hoppy.app.common.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new RequestInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/error");
    }
}
