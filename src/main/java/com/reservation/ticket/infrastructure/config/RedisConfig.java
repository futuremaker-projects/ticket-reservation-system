package com.reservation.ticket.infrastructure.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 설정
 */
@Configuration
public class RedisConfig {

    private static final String REDISSON_HOST_PREFIX = "redis";

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("%s://%s:%s".formatted(REDISSON_HOST_PREFIX, host, port))
                .setPassword(password);
        return Redisson.create(config);
    }

}
