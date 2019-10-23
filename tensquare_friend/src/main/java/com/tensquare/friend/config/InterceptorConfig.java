package com.tensquare.friend.config;

import com.tensquare.friend.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，声明拦截器对象和要拦截的请求
        registry.addInterceptor(jwtInterceptor)
                // 拦截路径
                .addPathPatterns("/**")
                // 不拦截路径
                .excludePathPatterns("/**/login/**");
    }
}
