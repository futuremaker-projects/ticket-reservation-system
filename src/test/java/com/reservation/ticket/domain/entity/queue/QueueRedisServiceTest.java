package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.config.WebTestConfig;
import com.reservation.ticket.support.config.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@Import({RedisConfig.class, WebTestConfig.class, QueueRedisService.class})
@SpringBootTest
class QueueRedisServiceTest {

    private final QueueRedisService queueRedisService;
    private RedisTemplate<String, String> redisTemplate;

    public QueueRedisServiceTest(
            @Autowired QueueRedisService queueRedisService,
            @Autowired RedisTemplate redisTemplate
    ) {
        this.queueRedisService = queueRedisService;
        this.redisTemplate = redisTemplate;
    }

    @DisplayName("Wait 대기열의 데이터 하나를 생성한다.")
    @Test
    void test01() {
        // given
        Long userId = 1L;

        // when
        String token = queueRedisService.createWaitQueue(userId);

        // then
        // 결과는 medis 에서 확인했습니다
    }

    @DisplayName("WAIT 대기열의 데이터를 ACTIVE로 변경한다.")
    @Test
    void test02() {
        // when
        queueRedisService.changeTokenStatusToActive();

        // then
        // 결과는 medis 에서 확인했습니다
    }

    @DisplayName("토큰을 이용하여 ACTIVE 대기열의 데이터르 삭제한다.")
    @Test
    void activeQueueRemoveByToken() {
        // given
        String token = "b02567dca468";

        // when
        queueRedisService.removeQueue(token);

        // then
    }
}