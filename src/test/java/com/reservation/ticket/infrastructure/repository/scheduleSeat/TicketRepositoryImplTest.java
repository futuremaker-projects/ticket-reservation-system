package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TicketRepositoryImplTest {

    @Autowired
    TicketRepository ticketRepository;

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

    @DisplayName("복수의 좌석 id를 이용하여 티켓 목록을 검색한다.")
    @Test
    public void select_test() {
        // given
        Long concertScheduleId = 1L;
        List<Long> seats = List.of(1L, 2L);

        // when
        List<Ticket> selected = ticketRepository.getSeats(concertScheduleId, seats);

        // then
        assertThat(selected.size()).isEqualTo(2);
        assertThat(selected)
                .extracting("createdAt")
                .containsExactlyInAnyOrder(
                        LocalDateTime.of(2024, 7, 9, 7,38, 29),
                        LocalDateTime.of(2024, 7, 10, 7,38, 29)
                );
    }

}