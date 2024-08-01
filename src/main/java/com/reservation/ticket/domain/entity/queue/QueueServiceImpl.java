package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.userAccount.UserAccountRepository;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService{

    private final QueueRepository queueRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public QueueCommand.Get createQueue(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        String token = generateToken();
        // 생성된 토큰을 사용자 정보에 저장
        userAccount.saveToken(token);
        // 새로운 대기열 데이터에 토큰 저장하여 생성
        QueueStatement statement = QueueStatement.of(userAccount, token, QueueStatus.WAIT);
        return QueueCommand.Get.from(queueRepository.save(statement));
    }

    @Transactional(readOnly = true)
    public List<QueueCommand.Get> selectQueueByStatus(QueueStatus status) {
        return queueRepository.getQueuesByStatus(status).stream()
                .map(QueueCommand.Get::from)
                .toList();
    }

    @Transactional
    public void renewQueueExpirationDate(String token) {
        QueueEntity queueEntity = queueRepository.getQueueByToken(token);
        queueEntity.extendShouldExpiredAt();
    }

    /**
     * 결재시 대기열 검증 후 만료
     *   - 대기열 토큰 유효성 검증
     *   - 대기열 만료
     */
    public void expireQueue(String token) {
        QueueEntity queueEntity = queueRepository.getQueueByToken(token);
        queueEntity.verifyQueueStatus();
        queueEntity.changeStatus(QueueStatus.EXPIRED);
    }

    public QueueEntity getQueueByToken(String token) {
        return queueRepository.getQueueByToken(token);
    }

    /**
     *  스케줄러 작업
     *   - ACTIVE 상태인 대기열을 검색하여 만료시간을 현재시간과 비교하여 5분이 초과했다면 EXPIRED 로 상태를 변경
     */
    @Transactional
    public void changeTokenStatusToExpire() {
        // ACTIVE 상태의 대기열을 EXPIRED 로 변경하는 작업
        List<QueueEntity> queuesAsActive = queueRepository.getQueuesByStatus(QueueStatus.ACTIVE);
        // ACTIVE 상태의 사용자가 한명도 없을때 리스트 확인
        if (!queuesAsActive.isEmpty()) {
            queuesAsActive.forEach(queue -> {
                // 만료시간이 현재시간 기준 5분 초과시 `EXPIRED` 로 변경
                if (queue.getShouldExpiredAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                    queue.changeStatus(QueueStatus.EXPIRED);
                }
            });
        }
    }

    /**
     *   - 제한된 인원(30명)을 기준으로 현재 ACTIVE 상태인 대기열 목록과 비교하여
     *      WAIT 상태인 대기열을 ACTIVE 로 변경
     */
    @Transactional
    @Override
    public void changeTokenStatusToActive() {
        // ACTIVE 상태의 대기열을 EXPIRED 로 변경하는 작업
        // 30명으로 인원 제한
        int maxAllowedActive = 30;
        List<QueueEntity> newQueueEntityAsActive = queueRepository.getQueuesByStatus(QueueStatus.ACTIVE);
        if (newQueueEntityAsActive.size() < maxAllowedActive) {
            // 제한된 인원(30명)과 ACTIVE 대기열 목록을 비교하여 `ACTIVE`로 변경
            int searchLimit = maxAllowedActive - newQueueEntityAsActive.size();
            List<QueueEntity> queuesAsWait = queueRepository.getQueuesByStatusPerLimit(QueueStatus.WAIT, searchLimit);
            queuesAsWait.forEach(queue -> queue.changeStatus(QueueStatus.ACTIVE));
        }
    }

    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }
}

