package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.complex.Ticket;

import java.util.List;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    List<Ticket> selectAllSeats();

    List<Ticket> getSeats(Long concertScheduleId, List<Long> seats);
    List<Ticket> getSeatsWithPessimisticLock(Long concertScheduleId, List<Long> seats);

    void removeSeats(List<Long> reservationIds);
}
