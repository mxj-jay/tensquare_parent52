package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorizeRequests 表示其一下均为需要的权限,是security注解实现的开端
        // 需要的权限分为两部分,第一部分是拦截的路径,第二部分访问该路径需要的权限
        // antMatchers表示拦截什么路径,permitAll任何权限都可以访问
        // anyRequest任何请求,authenticated认证后才能访问
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
