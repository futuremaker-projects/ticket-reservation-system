package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.queue.QueueServiceImpl;
import com.reservation.ticket.support.config.ScheduledConfig;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({ScheduledConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QueueEntitySchedulerTest {

    @Autowired
    SchedulerManager schedulerManager;

    @Autowired
    QueueServiceImpl queueService;

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

}
