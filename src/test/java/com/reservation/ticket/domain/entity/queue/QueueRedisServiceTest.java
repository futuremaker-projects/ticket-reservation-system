package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.infrastructure.dto.queue.ActiveQueueRedisDto;
import com.reservation.ticket.infrastructure.dto.queue.WaitingQueueRedisDto;
import com.reservation.ticket.infrastructure.repository.queue.ActiveQueueRedisRepository;
import com.reservation.ticket.infrastructure.repository.queue.WaitingQueueRedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QueueRedisServiceTest {

    @Autowired
    private QueueRedisService queueRedisService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ActiveQueueRedisRepository activeQueueRedisRepository;
    @Autowired
    private WaitingQueueRedisRepository waitingQueueRedisRepository;

    @BeforeEach
    void setUp() {
        // 테스트 메서드 실행시 redis 의 모든 데이터를 삭제
        removeAllKeys();
    }

    private void removeAllKeys() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @DisplayName("대기큐(Waiting Queue)에 하나의 대기열을 생성한다.")
    @Test
    void test01() {
        // given
        Long userId = 1L;

        // when
        String token = queueRedisService.createWaitQueue(userId);

        // then
        int one = 1;
        Set<WaitingQueueRedisDto> waitingQueues = queueRedisService.getWaitingQueues(one);
        assertThat(waitingQueues).isNotNull();
        assertThat(waitingQueues.size()).isEqualTo(one);

        WaitingQueueRedisDto dto = waitingQueues.iterator().next();
        assertThat(token).isEqualTo(dto.getToken());
    }

    @DisplayName("30개의 Waiting Queue를 Active Queue로 변경한다.")
    @Test
    void test02() {
        // given
        int totalTokenCount = 100;
        List<String> tokens = new ArrayList<>();

        LongStream.rangeClosed(1, totalTokenCount).forEach(i -> {
            String token = queueRedisService.createWaitQueue(i);
            tokens.add(token);
        });
        int limitOfActiveQueue = 30;

        // when
        queueRedisService.createActiveQueue(limitOfActiveQueue);

        // then
        List<ActiveQueueRedisDto> activeQueueList = new ArrayList<>();
        tokens.forEach(token -> {
            Optional<ActiveQueueRedisDto> queue = activeQueueRedisRepository.getQueueByToken(token);
            queue.ifPresent(activeQueueList::add);
        });

        Set<WaitingQueueRedisDto> waitingQueues = waitingQueueRedisRepository.getQueueByRange(0);

        assertThat(activeQueueList.size()).isEqualTo(limitOfActiveQueue);
        assertThat(waitingQueues.size()).isEqualTo(totalTokenCount - limitOfActiveQueue);
    }

    @DisplayName("token 및 userId로 대기큐의 단일 데이터를 삭제한다.")
    @Test
    void test05() {
        // given
        Long userId = 1L;
        // 대기큐 생성
        String token = queueRedisService.createWaitQueue(userId);
        // when
        // 대기큐 삭제
        waitingQueueRedisRepository.remove(token, userId);

        // then
        // 대기큐에 저장된 전체 데이터를 조회하는 메서드 - (-1)은 전체 데이터를 가져온다.
        Set<WaitingQueueRedisDto> queues = waitingQueueRedisRepository.getQueueByRange(-1);
        assertThat(queues.size()).isEqualTo(0);
    }

    @DisplayName("token 및 userId로 활성큐의 단일 데이터를 삭제한다.")
    @Test
    void test06() {
        // given
        // 활성큐 생성
        Long userId = 1L;
        String token = "b631a05093f7";
        activeQueueRedisRepository.save(token, userId);

        // when
        // 활성큐 삭제
        activeQueueRedisRepository.remove(token);

        // then
        Optional<ActiveQueueRedisDto> queue = activeQueueRedisRepository.getQueueByToken(token);
        assertThat(queue.isPresent()).isFalse();
    }

    @DisplayName("활성큐에 저장된 데이터를 token을 이용하여 검증한다.")
    @Test
    void verifyToken() {
        // given
        Long userId = 1L;
        String token = "b631a05093f7";
        activeQueueRedisRepository.save(token, userId);

        // when
        Optional<ActiveQueueRedisDto> queue = activeQueueRedisRepository.getQueueByToken(token);

        // then
        assertThat(queue.isPresent()).isTrue();
        assertThat(queue.get().getUserId()).isEqualTo(userId);
    }

    @DisplayName("활성큐의 만료시간을 갱신한다.(5분 - 300초)")
    @Test
    void test04() {
        // given
        Long userId = 1L;
        String token = "b631a05093f7";
        activeQueueRedisRepository.save(token, userId);

        // when
        activeQueueRedisRepository.extendExpiration(token);

        // then
        Long extendedTtl = activeQueueRedisRepository.getTtl(token);
        assertThat(extendedTtl).isEqualTo(300);
    }
}