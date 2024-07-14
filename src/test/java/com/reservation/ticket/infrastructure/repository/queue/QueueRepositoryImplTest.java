package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.repository.QueueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QueueRepositoryImplTest {

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
        List<Queue> allByQueueStatus = repository.findAllByQueueStatus(QueueStatus.ACTIVE);
        System.out.println("allByQueueStatus = " + allByQueueStatus);

        // then
    }

    @DisplayName("")
    @Test
    public void qpqpqpqp() {
        // given
        List<Queue> queues = repository.findAllByQueueStatus(QueueStatus.ACTIVE);
        System.out.println("queues.size() = " + queues.size());
        List<Queue> waits = List.of();
        if (queues.size() < 30) {
            int count = 30 - queues.size();
            waits = repository.findAllByQueueStatusPerLimit(QueueStatus.WAIT, count);
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
        Queue queueByUserId = repository.findQueueByUserId(userId);
        System.out.println("queueByUserId = " + queueByUserId);
        System.out.println("queueByUserId = " + queueByUserId.getUserAccount());

        // then
    }

}