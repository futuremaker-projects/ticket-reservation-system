package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.complex.Ticket;

import java.util.List;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    List<Ticket> selectAllSeats();
    List<Ticket> selectSeatsByScheduleIdWithPessimisticLock(Long concertScheduleId);
    List<Ticket> selectSeatsByScheduleIdWithOptimisticLock(Long concertScheduleId);
    List<Ticket> selectSeatsByScheduleId(Long concertScheduleId);

    List<Ticket> selectReservedSeats(Long concertScheduleId, List<Long> reservationIds);

    void removeSeats(List<Long> reservationIds);
}
