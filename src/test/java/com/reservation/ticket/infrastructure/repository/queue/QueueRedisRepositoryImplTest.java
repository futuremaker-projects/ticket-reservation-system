package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.config.WebTestConfig;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import com.reservation.ticket.support.config.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@Import({RedisConfig.class, WebTestConfig.class, QueueRedisRepositoryImpl.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QueueRedisRepositoryImplTest {

    private final QueueRedisRepositoryImpl queueRedisRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public QueueRedisRepositoryImplTest(
            @Autowired QueueRedisRepositoryImpl queueRedisRepository,
            @Qualifier("redisZsetTemplate") RedisTemplate redisTemplate
    ) {
        this.queueRedisRepository = queueRedisRepository;
        this.redisTemplate = redisTemplate;
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
    }
}