package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.entity.complex.ReservationSeatComplexIds;
import com.reservation.ticket.domain.repository.ReservationSeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ReservationSeatServiceTest {

    @InjectMocks ReservationSeatService sut;
    @Mock ReservationSeatRepository reservationSeatRepository;

    @DisplayName("선점되지 않은 좌석를 예약시 좌석이 선점된다.")
    @Test
    void given_when_then() {
        // given
        Long reservationId = 1L;
        Long concertScheduleId = 1L;
        Long seatId = 2L;
        List<Long> seatIds = List.of(1L, 2L, 3L);
        given(reservationSeatRepository.selectReservedSeatsByConcertScheduleId(concertScheduleId)).willReturn(reservationSeats());

        ReservationSeat reservationSeat = ReservationSeat.of(new ReservationSeatComplexIds(concertScheduleId, seatId, reservationId));
        given(reservationSeatRepository.save(any(ReservationSeat.class))).willReturn(reservationSeat);

        // when
        sut.save(reservationId, concertScheduleId, seatIds);

        // then
        then(reservationSeatRepository).should().selectReservedSeatsByConcertScheduleId(concertScheduleId);
        then(reservationSeatRepository).should(times(3)).save(any(ReservationSeat.class));
    }

    /**
     * TODO: 선점된 좌석 예약시 예외를 터뜨리는 메서드 필요하다.
     * @return
     */

    private List<ReservationSeat> reservationSeats() {
        return List.of(
                ReservationSeat.of(new ReservationSeatComplexIds(1L, 2L, 1L)),
                ReservationSeat.of(new ReservationSeatComplexIds(1L, 3L, 1L)),
                ReservationSeat.of(new ReservationSeatComplexIds(1L, 4L, 1L)),
                ReservationSeat.of(new ReservationSeatComplexIds(1L, 5L, 1L)),
                ReservationSeat.of(new ReservationSeatComplexIds(1L, 6L, 1L)),
                ReservationSeat.of(new ReservationSeatComplexIds(1L, 7L, 1L))
        );
    }

}