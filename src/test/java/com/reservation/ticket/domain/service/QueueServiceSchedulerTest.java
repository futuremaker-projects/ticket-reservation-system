package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.config.ScheduledConfig;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({ScheduledConfig.class})
//@SpringJUnitConfig(ScheduledConfig.class)
public class QueueServiceSchedulerTest {

    @Autowired
    private QueueService queueService;

    /**
     * 토큰 만료 - 만료시간이 경과하여 만료 (스케줄러 테스트)
     */
    @DisplayName("스케줄러가 돌며 등록일을 기준으로 5분이 지났을 때 상태값이 `ACTIVE`면 `EXPIRED`로 변경시킨다.")
    @Test
    void given_when_then() {
        // given
        List<QueueCommand.Get> beforeGet = queueService.selectQueueByStatus(QueueStatus.ACTIVE);
        assertThat(beforeGet).isNotNull();
        assertThat(beforeGet.size()).isEqualTo(28);

        // when
        queueService.changeTokenStatusExpired();

        // then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<QueueCommand.Get> gets = queueService.selectQueueByStatus(QueueStatus.ACTIVE);

                    assertThat(gets).isNotNull();
                    assertThat(gets.size()).isEqualTo(0);
                });
    }

    @DisplayName("")
    @Test
    public void given() {
        // given
        int maxAllowedActive = 30;
        // when
        List<QueueCommand.Get> gets = queueService.selectQueueByStatus(QueueStatus.ACTIVE);
        if (gets.size() < maxAllowedActive) {
            int searchSize = maxAllowedActive - gets.size();
            List<QueueCommand.Get> list = queueService.selectQueueByStatusPerLimit(QueueStatus.WAIT, searchSize);
            list.forEach(item -> {
                System.out.println("item = " + item);
            });
        }

        // then
    }
}
