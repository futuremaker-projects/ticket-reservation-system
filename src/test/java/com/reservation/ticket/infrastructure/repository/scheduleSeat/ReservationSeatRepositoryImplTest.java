package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.repository.ReservationSeatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReservationSeatRepositoryImplTest {

    @Autowired
    ReservationSeatRepository reservationSeatRepository;

    @Test
    void given_when_then() {
        // given
        Long concertScheduleId = 1L;
        List<Long> list = List.of(1L);

        // when
        List<ReservationSeat> reservationSeats = reservationSeatRepository.selectReservedSeats(concertScheduleId, list);

        // then
        for (ReservationSeat reservationSeat : reservationSeats) {
            System.out.println("scheduleSeat.getId().getConcertScheduleId() = " + reservationSeat.getId().getConcertScheduleId());
            System.out.println("scheduleSeat.getId().getSeatId() = " + reservationSeat.getId().getSeatId());
            System.out.println("reservationSeat.getId().getReservationId() = " + reservationSeat.getId().getReservationId());
            System.out.println();
        }
    }

}