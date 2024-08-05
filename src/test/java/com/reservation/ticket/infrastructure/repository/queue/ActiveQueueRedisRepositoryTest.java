package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.config.WebTestConfig;
import com.reservation.ticket.support.config.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@Import({RedisConfig.class, WebTestConfig.class, ActiveQueueRedisRepository.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActiveQueueRedisRepositoryTest {

    private final ActiveQueueRedisRepository activeQueueRedisRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public ActiveQueueRedisRepositoryTest(
            @Autowired ActiveQueueRedisRepository activeQueueRedisRepository,
            @Autowired RedisTemplate<String, String> redisTemplate
    ) {
        this.activeQueueRedisRepository = activeQueueRedisRepository;
        this.redisTemplate = redisTemplate;
    }

    @DisplayName("토큰을 이용하여 ACTIVE 대기열에 저장한다.")
    @Test
    public void test01() {
        // given

        // when

        // then
    }

    @DisplayName("토큰을 이용하여 ACTIVE 대기열의 데이터를 삭제한다.")
    @Test
    public void test02() {
        // given

        // when

        // then
    }

    @DisplayName("토큰을 이용하여 ACTIVE 대기열의 만료시간을 연장한다.")
    @Test
    public void test03() {
        // given

        // when

        // then
    }

    @DisplayName("토큰을 이용하여 ACTIVE 대기열의 데이터를 불러온다. - 토큰 유효성 검증")
    @Test
    public void test04() {
        // given

        // when

        // then
    }

}