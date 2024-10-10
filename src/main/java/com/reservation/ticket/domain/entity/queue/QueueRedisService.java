package com.reservation.ticket.domain.entity.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.userAccount.UserAccountRepository;
import com.reservation.ticket.infrastructure.dto.queue.ActiveQueueRedisDto;
import com.reservation.ticket.infrastructure.dto.queue.WaitingQueueRedisDto;
import com.reservation.ticket.infrastructure.repository.queue.ActiveQueueRedisRepository;
import com.reservation.ticket.infrastructure.repository.queue.WaitingQueueRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueRedisService {

    private final UserAccountRepository userAccountRepository;

    private final WaitingQueueRedisRepository waitingQueueRedisRepository;
    private final ActiveQueueRedisRepository activeQueueRedisRepository;

    /**
        대기큐 생성
    */
    public String createWaitQueue(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        String token = generateToken();
        // 생성된 토큰을 사용자 정보에 저장
        userAccount.saveToken(token);
        userAccountRepository.save(userAccount);
        waitingQueueRedisRepository.save(userAccount.getId(), token);
        return token;
    }

    /**
        활성큐 생성
            - 놀이공원 방식의 `대기열`을 구성
            - 일정 큐를 `대기큐`에서 `활성큐`로 변경
    */
    public void createActiveQueue(int limit) {
        Set<WaitingQueueRedisDto> waitingQueues = waitingQueueRedisRepository.getQueueByRange(limit);
        waitingQueues.forEach(dto -> {
            activeQueueRedisRepository.save(dto.getToken(), dto.getUserId());
            waitingQueueRedisRepository.remove(dto.getToken(), dto.getUserId());
        });
    }

    /**
        활성큐의 만료시간을 연장한다.
     */
    public void extendExpiration(String token) {
        activeQueueRedisRepository.extendExpiration(token);
    }

    /**
        대기큐의 순위를 리턴한다.
     */
    public Long getRank(String token, Long userId) {
        return waitingQueueRedisRepository.getRank(token, userId);
    }

    /**
        활성큐를 검증한다.
     */
    public boolean verify(String token) {
        return activeQueueRedisRepository.verify(token);
    }

    /**
        파라미터 범위만큼 대기큐를 조회한다.
     */
    public Set<WaitingQueueRedisDto> getWaitingQueues(int limit) {
        return waitingQueueRedisRepository.getQueueByRange(limit);
    }

    public ActiveQueueRedisDto getActiveQueue(String token) {
        Optional<ActiveQueueRedisDto> queue = activeQueueRedisRepository.getQueueByToken(token);
        return queue.orElse(null);
    }

    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }
}
