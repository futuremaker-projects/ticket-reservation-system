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

         *
          */

        // seat 저장


        //

        // when

        // then
    }

}