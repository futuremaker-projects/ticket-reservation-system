package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.config.WebTestConfig;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.dummy.DummyData;
import com.reservation.ticket.domain.entity.queue.Queue;
import com.reservation.ticket.infrastructure.dto.queue.statement.QueueStatement;
import com.reservation.ticket.support.config.RedisConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@Import({RedisConfig.class, WebTestConfig.class, QueueRedisRepository.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QueueRedisRepositoryTest {

    private final QueueRedisRepository queueRedisRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public QueueRedisRepositoryTest(
            @Autowired QueueRedisRepository queueRedisRepository,
            @Autowired RedisTemplate redisTemplate
    ) {
        this.queueRedisRepository = queueRedisRepository;
        this.redisTemplate = redisTemplate;
    }

    @BeforeEach
    public void init() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
        long userId = 1L;
        for (String token: DummyData.tokens()) {
            UserAccount userAccount = UserAccount.of(userId++);
            QueueStatement statement = QueueStatement.of(userAccount, token, QueueStatus.WAIT);
            queueRedisRepository.save(statement);
        }
    }

    @DisplayName("token과 유저 정보를 이용하여 waiting queue를 저장한다.")
    @Test
    void redisSave() {
        // given
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId);
        String token = "734488355d85";

        QueueStatement statement = QueueStatement.of(userAccount, token, QueueStatus.WAIT);

        // when
        queueRedisRepository.save(statement);

        // then
        Queue queueByToken = queueRedisRepository.getQueueByToken(QueueStatement.of(token, QueueStatus.WAIT));
        assertThat(queueByToken).isNotNull();
        assertThat(queueByToken.getToken()).isEqualTo(token);
    }

    @DisplayName("토큰을 이용하여 해당 토큰이 대기열에 존재하는지 확인한다.")
    @Test
    void redisGetByToken() {
        // given
        String token = "b02567dca467";

        // when
        queueRedisRepository.getQueueByToken(QueueStatement.of(token, QueueStatus.WAIT));

        // then
    }

    @DisplayName("WAIT 대기열의 정보를 정해진 수만큼 가져져온다.")
    @Test
    void redisGetQueuesByTokenPerLimit() {
        // given
        QueueStatus queueStatus = QueueStatus.WAIT;
        int limit = 9;

        // when
        List<Queue> queues = queueRedisRepository.getQueuesByStatusPerLimit(queueStatus, limit);

        // then
        assertThat(queues.size()).isEqualTo(limit + 1);
    }

    @DisplayName("token을 이용하여 Wait 대기열의 데이터를 지운다.")
    @Test
    void redisRemove() {
        // given
        String token = "734488355d85";

        // when
        queueRedisRepository.removeQueue(QueueStatement.of(token, QueueStatus.WAIT));
    }

}