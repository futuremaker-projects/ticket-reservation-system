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

}