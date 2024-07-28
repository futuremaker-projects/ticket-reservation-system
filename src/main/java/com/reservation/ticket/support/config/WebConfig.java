package com.reservation.ticket.support.config;

import com.reservation.ticket.support.config.filter.LogFilter;
import com.reservation.ticket.support.config.interceptor.TokenVerificationInterceptor;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenVerificationInterceptor tokenVerificationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenVerificationInterceptor)
                .addPathPatterns(       // 토큰검증이 필요한 URI
                        "/api/concertSchedules/**", "/api/payment/**",
                        "/api/queue/**", "/api/reservation/**"
                )
                .excludePathPatterns(   // swagger는 ignore
                        "/swagger-ui/**", "/v3/api-docs/**", "/error"
                );
    }

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}

