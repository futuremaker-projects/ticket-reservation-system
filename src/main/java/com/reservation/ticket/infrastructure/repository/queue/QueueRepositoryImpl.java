package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Queue save(Queue queue) {
        return queueJpaRepository.save(queue);
    }

    @Override
    public int countByStatus(QueueStatus status) {
        return queueJpaRepository.countQueueByQueueStatus(status);
    }

    @Override
    public Queue findByToken(String token) {
        return queueJpaRepository.findByToken(token);
    }
}
