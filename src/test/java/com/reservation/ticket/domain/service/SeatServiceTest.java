package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.entity.Seat;
import com.reservation.ticket.domain.repository.SeatRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        assertThat(seatCommands.size()).isEqualTo(seats.size());
    }

    @DisplayName("예약 id를 이용하여 좌석의 점유상태를 false에서 true로 변경한다. 점유 시간도 등록한다.")
    @Test
    public void 예약_id를_이용하여_좌석의_점유상태를_false에서_true로_변경한다_점유_시간도_등록한다() {
        // given
        Long reservationId = 1L;
        List<Long> seatIds = List.of(1L, 2L, 3L, 4L);
        List<Seat> seats = List.of(Seat.of(1L), Seat.of(2L), Seat.of(3L), Seat.of(4L));
        given(seatRepository.findByIdIn(seatIds)).willReturn(seats);

        // when
        sut.changeSeatOccupiedStatus(reservationId, seatIds);

        // then
        assertThat(seats.size()).isEqualTo(seatIds.size());
        assertThat(seats).extracting("id", "reservationId", "occupied")
                .containsExactlyInAnyOrder(
                    tuple(1L, reservationId, true),
                    tuple(2L, reservationId, true),
                    tuple(3L, reservationId, true),
                    tuple(4L, reservationId, true)
                );
    }

    @DisplayName("예약한 자리가 이미 점유중이면 예외를 발생한다.")
    @Test
    void 예약한_자리가_이미_점유중이면_예외를_발생한다() {
        // given
        Long reservationId = 1L;
        Long reservedId = 2L;
        List<Long> seatIds = List.of(1L, 2L, 3L, 4L);
        List<Seat> seats = List.of(Seat.of(1L), Seat.of(2L, reservedId), Seat.of(3L), Seat.of(4L));
        given(seatRepository.findByIdIn(seatIds)).willReturn(seats);

        // when
        Throwable t = catchThrowable(() -> sut.changeSeatOccupiedStatus(reservationId, seatIds));


        // then
        assertThat(t)
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Seat already occupied : %d".formatted(reservedId));
    }

    @DisplayName("점유된 자리를 예약된 자리의 시간이 만료됬을때 점유해제 시킨다.")
    @Test
    void 점유된_자리를_예약된_자리의_시간이_만료됬을때_점유해제_시킨다() {
        // given
        List<Long> reservationIds = List.of(1L, 2L, 3L, 4L);
        Long reservationId = 1L;
        Long concertScheduleId = 1L;
        boolean occupied = true;
        LocalDateTime occupiedAt = LocalDateTime.now();
        List<Seat> seats = List.of(
                Seat.of(1L, reservationId, concertScheduleId, occupied, occupiedAt),
                Seat.of(2L, reservationId, concertScheduleId, occupied, occupiedAt),
                Seat.of(3L, reservationId, concertScheduleId, occupied, occupiedAt),
                Seat.of(4L, reservationId, concertScheduleId, occupied, occupiedAt)
        );
        given(seatRepository.findAllByReservationIdIn(reservationIds)).willReturn(seats);

        // when
        sut.recoverSeatOccupiedStatus(reservationIds);

        // then
        assertThat(seats)
                .extracting("id", "reservationId", "occupied", "occupiedAt")
                .containsExactlyInAnyOrder(
                        tuple(1L, null, false, null),
                        tuple(2L, null, false, null),
                        tuple(3L, null, false, null),
                        tuple(4L, null, false, null)
                );
    }

}