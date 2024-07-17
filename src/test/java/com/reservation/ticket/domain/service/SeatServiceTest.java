package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.entity.Seat;
import com.reservation.ticket.domain.repository.SeatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @InjectMocks SeatService sut;

    @Mock SeatRepository seatRepository;

    @DisplayName("콘서트_스케줄_id로_좌석_목록조회")
    @Test
    public void 콘서트_스케줄_id로_좌석_목록조회() {
        // given
        Long concertScheduleId = 1L;
        List<Seat> seats = List.of(Seat.of(1L), Seat.of(2L), Seat.of(3L), Seat.of(4L));
        given(seatRepository.findAllByConcertScheduleId(concertScheduleId)).willReturn(seats);

        // when
        List<SeatCommand.Get> seatCommands = sut.selectSeatsByConcertScheduleId(concertScheduleId);

        // then
        Assertions.assertThat(seatCommands.size()).isEqualTo(seats.size());
    }

    @DisplayName("예약 id를 이용하여 좌석의 점유상태를 false에서 true로 변경한다. 점유 시간도 등록한다..")
    @Test
    public void given_when_then() {
        // given


        // when

        // then
    }

    /**
     * 예약한 자리가 이미 점유중이면 예외를 발생한다.
     */

    /**
     * 점유된 자리를 예약된 자리의 시간이 만료됬을때 점유해제 시킨다.
     */

}