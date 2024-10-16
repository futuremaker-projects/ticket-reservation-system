package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.queue.QueueService;
import com.reservation.ticket.infrastructure.dto.queue.ActiveQueueRedisDto;
import com.reservation.ticket.support.config.ScheduledConfig;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({ScheduledConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QueueSchedulerTest {

    @Autowired private SchedulerManager schedulerManager;

    @Autowired private QueueService queueService;

    @Autowired private QueueRedisService queueRedisService;
    @Autowired private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void clearQueues() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @DisplayName("등록일을 기준으로 5분이 지났을 때 상태값이 `ACTIVE`면 `EXPIRED`로 변경시킨다.")
    @Test
    public void given_when_then() {
        // given
        int activeCount = 30;

        List<QueueCommand.Get> beforeGet = queueService.selectQueueByStatus(QueueStatus.ACTIVE);
        assertThat(beforeGet).isNotNull();
        assertThat(beforeGet.size()).isEqualTo(activeCount);

        // when
        schedulerManager.activateQueueScheduler();

        // then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<QueueCommand.Get> gets = queueService.selectQueueByStatus(QueueStatus.ACTIVE);

                    int changeWaitToActiveCount = 15;
                    assertThat(gets).isNotNull();
                    assertThat(gets.size()).isEqualTo(changeWaitToActiveCount);
                });
    }

    @DisplayName("일정한 수량의 대기큐를 활성큐로 전환한다.")
    @Test
    void test02() {
        // given
        int limit = 300;
        String[] tokens = new String[1000];

        // 1000명의 유저에 대해 대기큐를 생성하고, 각 유저의 토큰을 저장
        for (long i = 1; i <= 1000; i++) {
            String token = queueRedisService.createWaitQueue(i);
            tokens[(int)i - 1] = token;
        }

        // when
        schedulerManager.activateQueueScheduler();

        // then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    // 300개의 활성큐가 잘 생성되었는지 검증
                    int activeQueueCount = 0;
                    for (int i = 0; i < limit; i++) {
                        // 토큰을 통해 활성 큐를 조회
                        ActiveQueueRedisDto activeQueue = queueRedisService.getActiveQueue(tokens[i]);
                        if (activeQueue != null) {
                            activeQueueCount++;
                        }
                    }

                    // 검증: 활성 큐의 개수와 크기 확인
                    assertEquals(limit, activeQueueCount, "활성 큐는 정확히 'limit'개의 항목을 포함해야 합니다");
                });

    }

}
