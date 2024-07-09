package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.repository.QueueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @InjectMocks QueueService queueService;
    @Mock QueueRepository queueRepository;

    @DisplayName("")
    @Test
    public void given_when_then() {
        /**
         *      1. user_id 를 이용한 user 검색
         *      2. token 생성 : UUID.substring
         *      3. status :
         *          1. 기존 대기열에 10개의 `ACTIVE` 가 존재한다면 `WAIT`으로 저장
         *          2. 기존 대기열에 10개 이하의 `ACTIVE` 가 존재시 `ACTIVE`로 저장
         *              - 동시성 상태에서 이게 가능한가... 진짜 복합키 써야 하는거 아닐까
         *                  - 정확히 되는지 동시성 테스트 돌려보고 수정하자
         *              - 복합키 의 키 (userId, token)
         *          3. Expired로 상태값을 변경해야 할때
         *              - 대기열에 있다가 나갈떄 이전의 대기열의 상태값을 `EXPIRED`로 변경 필요
         *              - expiredAt이 현재 시간과 비교하여 정해진 시간 외의 시간이 됬을때
         *
         */

        // 일단 가장 간단한 방법으로 풀어야 할까

        // given
        String uuid = UUID.randomUUID().toString();
        String result = uuid.substring(uuid.lastIndexOf("-") + 1);

        System.out.println("uuid = " + uuid);
        System.out.println("result = " + result);

        // when

        // then
    }

}