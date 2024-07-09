package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks ReservationService sut;
    @Mock ReservationRepository reservationRepository;

    /**
     * 예약을 진행한다. 예약시 콘서트 스케줄 id, 자리 id 필요
     *
     */
    @Test
    void given_when_then() {
        // given
        Long concertScheduleId = 1L;
        List<Integer> seats = List.of(1, 2);
        /**
         *  reservation 저장
         *      1. user_id 를 이용한 user 검색
         *      2. token 생성 : UUID.substring
         *      3. status :
         *          1. 기존 대기열에 10개의 `ACTIVE` 가 존재한다면 WAIT으로 저장
         *          2. 기존 대기열에 10개 이하의 `ACTIVE` 가 존재시 `ACTIVE`로 저장
         *              - 동시성 상태에서 이게 가능한가... 진짜 복합키 써야 하는거 아닐까
         *                  - 정확히 되는지 테스트 해봐야 할거 같다.
         *              - 복합키 의 키 (userId, token)
         *          3. Expired로 상태값을 변경해야 할때
         *              - 대기열에 있다가 나갈떄 이전의 대기열의 상태값을 `EXPIRED`로 변경 필요
         *              - expiredAt이 현재 시간과 비교하여 정해진 시간 외의 시간이 됬을때
         *          4.
         *
          */

        // seat 저장
        String uuid = UUID.randomUUID().toString();
        String result = uuid.substring(uuid.lastIndexOf("-") + 1);

        System.out.println("uuid = " + uuid);
        System.out.println("result = " + result);

        //

        // when

        // then
    }

}