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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @InjectMocks
    QueueService sut;
    @Mock
    QueueRepository queueRepository;
    @Mock
    UserAccountRepository userAccountRepository;

    /**
     * 토큰 생성 -
     */
    @DisplayName("토큰 생성 - 대기열 생성시에 `대기상태(WAIT)로 저장")
    @Test
    void givenUserId_whenActiveQueueIsOver30_thenSaveQueueAsWaitStatus() {
        // given
        // userId로 user 검색
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId, "Sofia");
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        // 대기열 데이터 생성
        Long queueId = 21L;
        String token = generateToken();

        Queue savedQueue = Queue.of(queueId, userAccount, token, QueueStatus.WAIT);

        given(queueRepository.save(any(Queue.class))).willReturn(savedQueue);

        // when
        QueueCommand.Get getQueue = sut.createQueue(userId);

        // then
        assertThat(getQueue).isNotNull();
        assertThat(getQueue.id()).isEqualTo(savedQueue.getId());
        assertThat(getQueue.status()).isEqualTo(QueueStatus.WAIT);
        assertThat(getQueue.token()).isEqualTo(savedQueue.getToken());

        then(userAccountRepository).should().findById(userId);
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

        String token = userAccount.getToken();

        Long queueId = 21L;
        Queue queue = Queue.of(queueId, userAccount, token, QueueStatus.ACTIVE);
        given(queueRepository.findQueueByUserId(userId)).willReturn(queue);

        // when
        QueueCommand.Get getQueue = sut.getQueueByUserId(userId);

        // then
        assertThat(getQueue).isNotNull();
        assertThat(getQueue.id()).isEqualTo(queueId);
        assertThat(getQueue.token()).isEqualTo(token);
        assertThat(getQueue.status()).isEqualTo(QueueStatus.ACTIVE);

        then(queueRepository).should().findQueueByUserId(userId);
    }

    /**
     * 토큰 만료 - 직접만료 -> 구매가 끝남       // 테스트하는 목적이 부정확함 -> 테스트하면 당연히 통과되야되는 구조
     */
    @DisplayName("")
    @Test
    void givenUserId_whenSearchingQueue_thenExpiredQueueStatus() {
        // given
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId, "Sofia", generateToken());

        Long queueId = 21L;
        String token = userAccount.getToken();
        Queue queue = Queue.of(queueId, userAccount, token, QueueStatus.ACTIVE);
        given(queueRepository.findQueueByUserId(userId)).willReturn(queue);

        queue.changeStatus(QueueStatus.EXPIRED);

        // when
        sut.expireQueueByChangingStatus(userId);

        // then
        assertThat(queue.getQueueStatus()).isEqualTo(QueueStatus.EXPIRED);

        then(queueRepository).should().findQueueByUserId(userId);
    }

    /**
     *
     */
    @DisplayName("`ACTIVE` 상태의 대기열이 30개 이하일 때 변경할 대기상태(`WAIT`)의 데이터 목록조회")
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

        // 찾은 토큰으로 대기열 데이터를 가져옴
        Long queueId = 21L;
        LocalDateTime shouldExpiredAt = LocalDateTime.of(2024, 7, 10, 10, 10, 5);
        LocalDateTime createdAt = LocalDateTime.of(2024, 7, 10, 10, 5, 5);

        Queue queue = Queue.of(queueId, userAccount.getToken(), QueueStatus.ACTIVE, shouldExpiredAt, createdAt);
        given(queueRepository.findQueueByUserId(userId)).willReturn(queue);



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
