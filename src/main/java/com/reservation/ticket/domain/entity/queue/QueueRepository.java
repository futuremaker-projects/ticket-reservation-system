package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.queue.statement.QueueStatement;

import java.util.List;

public interface QueueRepository {

    List<Queue> getQueuesByStatus(QueueStatus queueStatus);

    List<Queue> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit);

    Queue getQueueByToken(QueueStatement statement);

    int countByStatus(QueueStatus status);

    Queue save(QueueStatement statement);

    Queue getQueueByUserId(Long userId);

    void removeQueue(QueueStatement statement);

    void removeQueues(QueueStatus queueStatus, List<String> queues);
}
