package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.repository.queue.QueueRedisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueRedisService implements QueueService{

    private final QueueRedisRepositoryImpl queueRedisRepositoryImpl;

    @Override
    public QueueCommand.Get createQueue(Long userId) {
        return null;
    }

    @Override
    public List<QueueCommand.Get> selectQueueByStatus(QueueStatus status) {
        return List.of();
    }

    @Override
    public void renewQueueExpirationDate(String token) {

    }

    @Override
    public void expireQueue(String token) {

    }

    @Override
    public void changeTokenStatusToExpire() {

    }

    @Override
    public void changeTokenStatusToActive() {

    }
}
