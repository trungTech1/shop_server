package com.example.shop_sv.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenInterceptor())
               .addPathPatterns("/user/authen")
                .addPathPatterns("/categories")
                .addPathPatterns("/user/list")
                .addPathPatterns("/user/update")
        .addPathPatterns("/auth");
               ; // Đường dẫn mà interceptor sẽ áp dụng, ví dụ "/*" hoặc "/api/**"
    }
}
