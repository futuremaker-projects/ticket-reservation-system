package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.support.config.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("waiting queue redis test")
@Import({RedisConfig.class})
class WaitingQueueRedisRepositoryTest {



}