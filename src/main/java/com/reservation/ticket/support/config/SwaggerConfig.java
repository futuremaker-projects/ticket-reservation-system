package com.reservation.ticket.support.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .info(apiInfo());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }

    private Info apiInfo() {
        return new Info()
                .title("API 명세서")            // API의 제목
                .description("콘서트 예약 API")    // API에 대한 설명
                .version("1.0.0");            // API의 버전
    }

}
