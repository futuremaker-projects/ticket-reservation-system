package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.enums.QueueStatus;

import java.util.List;

public interface QueueService {

    QueueCommand.Get createQueue(Long userId);

    List<QueueCommand.Get> selectQueueByStatus(QueueStatus status);

    void renewQueueExpirationDate(String token);
    void expireQueue(String token);

    QueueCommand.Get getQueueByToken(String token);
    void verifyQueue(String token);

    void changeTokenStatusToExpire();
    void changeTokenStatusToActive();

}
