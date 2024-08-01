package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.queue.QueueServiceImpl;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.queue.QueueRepository;
import com.reservation.ticket.domain.entity.userAccount.UserAccountRepository;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class QueueEntityServiceTest {

    @InjectMocks
    QueueServiceImpl sut;
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

        QueueEntity savedQueueEntity = QueueEntity.of(queueId, userAccount, token, QueueStatus.WAIT);

        given(queueRepository.save(any(QueueStatement.class))).willReturn(savedQueueEntity);

        // when
        QueueCommand.Get getQueue = sut.createQueue(userId);

        // then
        assertThat(getQueue).isNotNull();
        assertThat(getQueue.id()).isEqualTo(savedQueueEntity.getId());
        assertThat(getQueue.status()).isEqualTo(QueueStatus.WAIT);
        assertThat(getQueue.token()).isEqualTo(savedQueueEntity.getToken());

        then(userAccountRepository).should().findById(userId);
        then(queueRepository).should().save(any(QueueStatement.class));
    }

    @DisplayName("사용자 토큰을 이용하여 대기열을 만료상태로 변경한다.")
    @Test
    public void givenTokenInUser_whenRequestingExpireQueue_thenChangeQueueStatusAsExpired() {
        // given
        Long queueId = 1L;
        String token = generateToken();
        QueueEntity queueEntity = QueueEntity.of(queueId, token, QueueStatus.ACTIVE);
        given(queueRepository.getQueueByToken(token)).willReturn(queueEntity);

        // when
        sut.expireQueue(token);

        // then
        assertThat(queueEntity.getQueueStatus()).isEqualTo(QueueStatus.EXPIRED);
        then(queueRepository).should().getQueueByToken(token);
    }


    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }
}
