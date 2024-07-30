package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.domain.entity.concert.reservation.ticket.Ticket;
import com.reservation.ticket.domain.entity.concert.reservation.ticket.TicketComplexIds;
import com.reservation.ticket.domain.entity.concert.reservation.ticket.TicketRepository;
import com.reservation.ticket.domain.exception.ApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    ReservationService sut;
    @Mock
    TicketRepository ticketRepository;

    @DisplayName("선점되지 않은 좌석를 예약시 좌석이 선점된다.")
    @Test
    void given_when_then() {
        // given
        Long reservationId = 1L;
        Long concertScheduleId = 1L;
        Long seatId = 2L;
        List<Long> seatIds = List.of(1L, 2L, 3L);
        given(ticketRepository.getSeats(concertScheduleId, seatIds)).willReturn(reservationSeats());

        Ticket ticket = Ticket.of(new TicketComplexIds(concertScheduleId, seatId, reservationId));
        given(ticketRepository.issue(any(Ticket.class))).willReturn(ticket);

        // when
//        sut.reserve(reservationId, concertScheduleId, seatIds, LockModeType.NONE);

        // then
        then(ticketRepository).should().getSeats(concertScheduleId, seatIds);
        then(ticketRepository).should(times(3)).issue(any(Ticket.class));
    }

    /**
     * TODO: 선점된 좌석 예약시 예외를 터뜨리는 메서드 필요하다.
     * @return
     */
    @DisplayName("선점된 좌석 예약시 예외를 발생시킨다.")
    @Test
    void given_when_then2() {
        // given
        Long concertScheduleId = 1L;
        List<Long> seatIds = List.of(5L, 6L, 7L);
        given(ticketRepository.getSeats(concertScheduleId, seatIds)).willReturn(reservationSeats());

        List<Long> reservedSeatIds = reservationSeats().stream().map(reservationSeat -> reservationSeat.getId().getSeatId()).toList();
        ArrayList<Long> copiedSeatIds = new ArrayList<>(seatIds);
        copiedSeatIds.retainAll(reservedSeatIds);       // 이미 예약된 좌석이면 예외처리 한다.

        // when
        Throwable t = catchThrowable(() -> sut.checkIfSeatsAvailable(concertScheduleId, seatIds));

        // then
        assertThat(t)
                .isInstanceOf(ApplicationException.class)
                .hasMessage("seat already occupied : %s".formatted(copiedSeatIds));
    }

    private List<Ticket> reservationSeats() {
        return List.of(
                Ticket.of(new TicketComplexIds(1L, 4L, 1L)),
                Ticket.of(new TicketComplexIds(1L, 5L, 1L)),
                Ticket.of(new TicketComplexIds(1L, 6L, 1L)),
                Ticket.of(new TicketComplexIds(1L, 7L, 1L))
        );
    }

}