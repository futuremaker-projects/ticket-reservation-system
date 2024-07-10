package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.repository.QueueRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<QueueCommand.Get> selectQueueByStatus(QueueStatus status) {
        return queueRepository.findAllByQueueStatus(status).stream()
                .map(QueueCommand.Get::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QueueCommand.Get> selectQueueByStatusPerLimit(QueueStatus status, int limit) {
        return queueRepository.findAllByQueueStatusPerLimit(status, limit).stream()
                .map(QueueCommand.Get::from)
                .toList();
    }

    public QueueCommand.Get getQueueByUserId(Long userId) {
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

    public boolean verifyToken(Queue queue) {
        return queue.getQueueStatus() == QueueStatus.ACTIVE;
    }

    public QueueCommand.Get renewExpirationDate(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        Queue queue = queueRepository.findByToken(userAccount.getToken());
        queue.extendShouldExpiredAt();
        return QueueCommand.Get.from(queue);
    }

    /**
     * 대기열 생성 시에 모두 WAIT으로 만들고 줄을 세운다
     *  스케줄러를 돌려서 ACTIVE 로 변경시키는 작업을 해준다.
     */
    @Transactional
    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void changeTokenStatusExpired() {
        List<Queue> queuesAsActive = queueRepository.findAllByQueueStatus(QueueStatus.ACTIVE);
        // 사용자가 들어오기 전에
        if (!queuesAsActive.isEmpty()) {
            queuesAsActive.forEach(queue -> {
                if (queue.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                    queue.changeStatus(QueueStatus.EXPIRED);
                }
            });
        }

        // 1 안. ACTIVE -> EXPIRED 로 변경된 수 만큼의 WAIT 사용자를 ACTIVE 로 변경 시킨다.
        // 2 안. ACTIVE 사용자를 다시 검색해서 30에서 뺀후 들어간다.
//        int maxAllowedActive = 30;
//        List<Queue> queuesAsActive = queueRepository.findAllByQueueStatus(QueueStatus.ACTIVE);
//        if (queuesAsActive.size() < maxAllowedActive) {
//            int searchSize = maxAllowedActive - queuesAsActive.size();
//            List<Queue> queuesAsWait = queueRepository.findAllByQueueStatusPerLimit(QueueStatus.WAIT, searchSize);
//            queuesAsWait.forEach(queue -> {
//                queue.changeStatus(QueueStatus.ACTIVE);
//            });
//        }
    }



//    @Transactional
//    @Scheduled(cron = "8 * * * * *", zone = "Asia/Seoul")
//    public void changeTokenStatusActive() {
//        int maxAllowedActive = 30;
//        List<QueueCommand.Get> queuesAsActive = selectQueueByStatus(QueueStatus.ACTIVE);
//        if (queuesAsActive.size() < maxAllowedActive) {
//            int searchSize = maxAllowedActive - queuesAsActive.size();
//            List<Queue> queuesAsWait = queueRepository.findAllByQueueStatusPerLimit(QueueStatus.WAIT, searchSize);
//            queuesAsWait.forEach(queue -> {
//                queue.changeStatus(QueueStatus.ACTIVE);
//            });
//        }
//    }

    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }
}
