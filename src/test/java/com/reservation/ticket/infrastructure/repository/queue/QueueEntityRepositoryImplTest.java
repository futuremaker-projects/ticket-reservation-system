package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.queue.QueueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QueueEntityRepositoryImplTest {

    @Autowired
    private QueueRepository repository;

    @Test
    void wowo() {
        // given
        int i = repository.countByStatus(QueueStatus.ACTIVE);
        System.out.println("i = " + i);

        // when

        // then
    }

    @DisplayName("")
    @Test
    public void wowowo() {
        // given

        // when
        List<QueueEntity> allByQueueEntityStatuses = repository.getQueuesByStatus(QueueStatus.ACTIVE);
        System.out.println("allByQueueStatus = " + allByQueueEntityStatuses);

        // then
    }

    @DisplayName("")
    @Test
    public void qpqpqpqp() {
        // given
        List<QueueEntity> queueEntities = repository.getQueuesByStatus(QueueStatus.ACTIVE);
        System.out.println("queues.size() = " + queueEntities.size());
        List<QueueEntity> waits = List.of();
        if (queueEntities.size() < 30) {
            int count = 30 - queueEntities.size();
            waits = repository.getQueuesByStatusPerLimit(QueueStatus.WAIT, count);
            System.out.println("waits.size() = " + waits.size());
        }

        // when
//        System.out.println("waits = " + waits);


        // then
    }

    @Test
    void eoeoeo() {
        // given
        Long userId = 1L;

        // when
        QueueEntity queueEntityByUserId = repository.getQueueByUserId(userId);
        System.out.println("queueByUserId = " + queueEntityByUserId);
        System.out.println("queueByUserId = " + queueEntityByUserId.getUserAccount());

        // then
    }

}