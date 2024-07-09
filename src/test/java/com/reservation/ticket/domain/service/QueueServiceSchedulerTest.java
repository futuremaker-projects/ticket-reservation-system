package com.reservation.ticket.domain.service;

import com.reservation.ticket.infrastructure.config.ScheduledConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ScheduledConfig.class)
public class QueueServiceSchedulerTest {

    @Autowired
    private QueueService queueService;

    /**
     * 토큰 만료 - 만료시간이 경과하여 만료 (스케줄러 테스트)
     */
    @DisplayName("")
    @Test
    void given_when_then() {
        // given

        // when

        // then
    }

}
