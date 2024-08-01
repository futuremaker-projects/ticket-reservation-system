package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.repository.queue.QueueRedisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueRedisServiceImpl implements QueueService {

    private final QueueRepository queueRepository;

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
    public QueueCommand.Get getQueueByToken(String token) {
        return null;
    }

    @Override
    public void verifyQueue(String token) {
        QueueEntity queue = queueRepository.getQueueByToken(token);

    }

    @Override
    public void changeTokenStatusToExpire() {

    }

    @Override
    public void changeTokenStatusToActive() {

    }
}
