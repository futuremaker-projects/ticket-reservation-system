package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TicketRepositoryImplTest {

    @Autowired
    TicketRepository ticketRepository;

    @DisplayName("")
    @Test
    void given_when_then() {
        // given
        Long concertScheduleId = 1L;
        List<Long> list = List.of(1L);

        // when
        List<Ticket> tickets = ticketRepository.selectReservedSeats(concertScheduleId, list);

        // then


    }

    @DisplayName("예약된 점유 좌석을 삭제한다.")
    @Test
    public void remove_test() {
        // given
        List<Long> reservationIds = List.of(1L, 3L);

        // when
        ticketRepository.removeSeats(reservationIds);

        // then
        List<Ticket> tickets = ticketRepository.selectAllSeats();

        assertThat(tickets)
                .extracting(reservationSeat -> reservationSeat.getId().getSeatId())
                .containsExactlyInAnyOrder(1L, 2L, 1L);
    }

}