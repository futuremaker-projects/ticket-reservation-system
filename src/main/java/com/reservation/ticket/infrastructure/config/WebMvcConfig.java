package com.reservation.ticket.infrastructure.config;

import com.reservation.ticket.infrastructure.config.interceptor.TokenVerificationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

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
}
