package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.repository.QueueRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @InjectMocks QueueService sut;
    @Mock QueueRepository queueRepository;
    @Mock UserAccountRepository userAccountRepository;

    @DisplayName("토큰 생성 - 활서화 상태의 사용자가 제한된 사용자(30명)보다 적을때")
    @Test
    public void givenUserId_whenActiveQueueIsUnder30_thenSaveQueueAsActiveStatus() {
        // given
        // userId로 user 검색
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId, "Sofia");
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        // 대기열 상태값 - Active(티켓 구매가능) 상태의 대기자가 30명(제한된 인원)보다 적다면
        // 대기열의 데이터를 Active 로 저장
        int underCount = 20;
        given(queueRepository.countByStatus(QueueStatus.ACTIVE)).willReturn(underCount);

        // 대기열 데이터 생성
        Long queueId = 21L;
        String token = generateToken();

        Queue queue = Queue.of();
        queue.saveStatusInQueue(underCount);
        queue.saveData(userAccount, token);
        Queue savedQueue = Queue.of(queueId, userAccount, token, QueueStatus.ACTIVE);

        given(queueRepository.save(any(Queue.class))).willReturn(savedQueue);

        // when
        QueueCommand.Get getQueue = sut.createQueue(userId);

        // then
        assertThat(getQueue).isNotNull();
        assertThat(getQueue.id()).isEqualTo(savedQueue.getId());
        assertThat(getQueue.status()).isEqualTo(QueueStatus.ACTIVE);
        assertThat(getQueue.token()).isEqualTo(queue.getToken());

        then(userAccountRepository).should().findById(userId);
        then(queueRepository).should().countByStatus(QueueStatus.ACTIVE);
        then(queueRepository).should().save(any(Queue.class));
    }


    /**
     * 토큰 생성 -
     */
    @DisplayName("토큰 생성 - 활서화 상태의 사용자가 제한된 사용자보다 만을때")
    @Test
    void givenUserId_whenActiveQueueIsOver30_thenSaveQueueAsWaitStatus() {
        // given
        // userId로 user 검색
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId, "Sofia");
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        // 대기열 상태값 - Active(티켓 구매가능) 상태의 대기자가 30명(제한된 인원)보다 많다면
        // 대기열의 데이터를 Wait 로 저장
        int overCount = 30;
        given(queueRepository.countByStatus(QueueStatus.ACTIVE)).willReturn(overCount);

        // 대기열 데이터 생성
        Long queueId = 21L;
        String token = generateToken();

        Queue queue = Queue.of();
        queue.saveStatusInQueue(overCount);
        queue.saveData(userAccount, token);
        Queue savedQueue = Queue.of(queueId, userAccount, token, QueueStatus.WAIT);

        given(queueRepository.save(any(Queue.class))).willReturn(savedQueue);

        // when
        QueueCommand.Get getQueue = sut.createQueue(userId);

        // then
        assertThat(getQueue).isNotNull();
        assertThat(getQueue.id()).isEqualTo(savedQueue.getId());
        assertThat(getQueue.status()).isEqualTo(QueueStatus.WAIT);
        assertThat(getQueue.token()).isEqualTo(queue.getToken());

        then(userAccountRepository).should().findById(userId);
        then(queueRepository).should().countByStatus(QueueStatus.ACTIVE);
        then(queueRepository).should().save(any(Queue.class));
    }

    /**
     * 토큰 검색
     */
    @Test
    void givenToken_whenRequestingQueue_thenReturnQueue() {
        // given
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId, "Sofia");
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        String token = userAccount.getToken();

        Long queueId = 21L;
        Queue queue = Queue.of(queueId, userAccount, token, QueueStatus.ACTIVE);
        given(queueRepository.findByToken(token)).willReturn(queue);

        // when
        QueueCommand.Get getQueue = sut.getQueueByUserId(userId);

        // then
        assertThat(getQueue).isNotNull();
        assertThat(getQueue.id()).isEqualTo(queueId);
        assertThat(getQueue.token()).isEqualTo(token);
        assertThat(getQueue.status()).isEqualTo(QueueStatus.ACTIVE);

        then(userAccountRepository).should().findById(userId);
        then(queueRepository).should().findByToken(token);
    }

    /**
     * 토큰 만료 - 직접만료 -> 구매가 끝남
     */
    @DisplayName("")
    @Test
    void givenUserId_whenSearchingQueue_thenExpiredQueueStatus() {
        // given
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId, "Sofia", generateToken());
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        Long queueId = 21L;
        String token = userAccount.getToken();
        Queue queue = Queue.of(queueId, userAccount, token, QueueStatus.ACTIVE);
        given(queueRepository.findByToken(token)).willReturn(queue);

        queue.changeStatus(QueueStatus.EXPIRED);

        // when
        sut.expireQueueByChangingStatus(userId);

        // then
        assertThat(queue.getQueueStatus()).isEqualTo(QueueStatus.EXPIRED);

        then(userAccountRepository).should().findById(userId);
        then(queueRepository).should().findByToken(token);
    }

    /**
     *
     */
    @DisplayName("`ACTIVE` 상태의 대기열이 30개 이하일 때 변경할 `WAIT`의 데이터 조회")
    @Test
    public void given_when() {
        // given

        // when

        // then
    }

    /**
     * 대기열 만료시간 갱신
     */
    @Test
    void givenTokenInUser_whenRequestingRenewExpireDate_thenRenewExpireDate() {
        // given
        // 사용자를 검색하여 토큰을 찾음
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId, "Sofia", generateToken());
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        // 찾은 토큰으로 대기열 데이터를 가져옴
        Long queueId = 21L;
        LocalDateTime shouldExpiredAt = LocalDateTime.of(2024, 7, 10, 10, 10, 5);
        LocalDateTime createdAt = LocalDateTime.of(2024, 7, 10, 10, 5, 5);

        Queue queue = Queue.of(queueId, userAccount.getToken(), QueueStatus.ACTIVE, shouldExpiredAt, createdAt);
        given(queueRepository.findByToken(queue.getToken())).willReturn(queue);

        LocalDateTime renewedExpiredAt = LocalDateTime.of(2024, 7, 10, 10, 15, 5);
        Queue renewedQueue = Queue.of(queueId, userAccount.getToken(), QueueStatus.ACTIVE, renewedExpiredAt, createdAt);

        // when
        QueueCommand.Get queueCommand = sut.renewExpirationDate(userId);

        // then
        assertThat(queueCommand).isNotNull();
        assertThat(queueCommand.shouldExpiredAt()).isEqualTo(renewedQueue.getShouldExpiredAt());
    }


    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }
}