package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.domain.enums.OutboxType;
import com.reservation.ticket.infrastructure.dto.entity.OutboxEntity;
import com.reservation.ticket.infrastructure.repository.common.OutboxRepositoryImpl;
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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import({ScheduledConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OutboxSchedulerTest {

    @Autowired
    SchedulerManager schedulerManager;

    @Autowired
    OutboxRepositoryImpl outboxRepository;

    @DisplayName("5분이 경과된 outbox 데이터 중 'INIT' 상태인 데이터를 다시 메시지로 전송하여 'PUBLISHED'로 변경한다.")
    @Test
    void test01() {
        // given
        int initMessages = 4;
        List<OutboxEntity> outboxes = outboxRepository.getOutboxByOutboxType(OutboxType.INIT);
        assertThat(outboxes.size()).isEqualTo(initMessages);

        // when
        schedulerManager.resendMessage();

        // then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    int publishedMessages = 4;
                    List<OutboxEntity> changedOutboxes = outboxRepository.getOutboxByOutboxType(OutboxType.PUBLISHED);
                    assertThat(changedOutboxes).isNotEmpty();
                    assertThat(changedOutboxes.size()).isEqualTo(publishedMessages);
                });
    }

}
