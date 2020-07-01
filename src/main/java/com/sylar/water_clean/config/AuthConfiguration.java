package com.sylar.water_clean.config;

import com.sylar.water_clean.Interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author ：Sylar
 * @date ：Created in 2019/10/16 11:04
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
@SuppressWarnings("deprecation")
public class AuthConfiguration extends WebMvcConfigurerAdapter {

    //此处用于解决拦截器无法注入service的问题
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/error", "/login_form", "/home", "/login", "/noAuth", "/timeOut", "/my/**");
    }

}
