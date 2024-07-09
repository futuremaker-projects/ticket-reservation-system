package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.repository.QueueRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public QueueCommand.Get createQueue(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);

        int countActiveStatus = queueRepository.countByStatus(QueueStatus.ACTIVE);

        Queue queue = Queue.of();
        queue.saveStatusInQueue(countActiveStatus);
        queue.saveData(userAccount, generateToken());

        return QueueCommand.Get.from(queueRepository.save(queue));
    }

    public QueueCommand.Get getQueue(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        Queue queue = queueRepository.findByToken(userAccount.getToken());
        return QueueCommand.Get.from(queue);
    }

    @Transactional
    public void expireQueueByChangingStatus(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        Queue queue = queueRepository.findByToken(userAccount.getToken());
        queue.changeStatus(QueueStatus.EXPIRED);
    }



    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }
}
