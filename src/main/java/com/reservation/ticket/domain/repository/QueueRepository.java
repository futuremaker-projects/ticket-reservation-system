package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;

import java.util.List;

public interface QueueRepository {

    List<Queue> findAllByQueueStatus(QueueStatus queueStatus);

    List<Queue> findAllByQueueStatusPerLimit(QueueStatus queueStatus, int limit);

    Queue findByToken(String token);

    int countByStatus(QueueStatus status);

    Queue save(Queue queue);

    Queue findQueueByUserId(Long userId);
}
