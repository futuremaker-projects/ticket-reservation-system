package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;

public interface QueueRepository {

    Queue save(Queue queue);

    int countByStatus(QueueStatus status);

    Queue findByToken(String token);
}
