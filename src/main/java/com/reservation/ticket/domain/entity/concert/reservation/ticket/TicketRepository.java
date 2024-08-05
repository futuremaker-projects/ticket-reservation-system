package com.reservation.ticket.domain.entity.concert.reservation.ticket;

import java.util.List;

public interface TicketRepository {

    Ticket issue(Ticket ticket);

    List<Ticket> selectAllSeats();

    List<Ticket> getSeats(Long concertScheduleId, List<Long> seats);
    List<Ticket> getSeatsWithPessimisticLock(Long concertScheduleId, List<Long> seats);

    void removeSeats(List<Long> reservationIds);
}
