package com.dmm.common.configuration;

import com.dmm.common.Interceptor.PjaxInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {


    @Bean
    PjaxInterceptor pjaxInterceptor() {
        return new PjaxInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(pjaxInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
