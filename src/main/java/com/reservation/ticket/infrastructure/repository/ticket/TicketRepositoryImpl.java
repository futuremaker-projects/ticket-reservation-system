package com.reservation.ticket.infrastructure.repository.ticket;

import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    @Override
    public Ticket save(Ticket ticket) {
        return ticketJpaRepository.save(ticket);
    }

    @Override
    public List<Ticket> selectAllSeats() {
        return ticketJpaRepository.findAll();
    }

    @Override
    public List<Ticket> selectSeatsByScheduleIdWithPessimisticLock(Long concertScheduleId) {
        return ticketJpaRepository.findAllByIdConcertScheduleIdWithPessimisticLock(concertScheduleId);
    }

    @Override
    public List<Ticket> selectSeatsByScheduleIdWithOptimisticLock(Long concertScheduleId) {
        return ticketJpaRepository.findAllByIdConcertScheduleIdWithOptimisticLock(concertScheduleId);
    }

    @Override
    public List<Ticket> selectSeatsByScheduleId(Long concertScheduleId) {
        return ticketJpaRepository.findReservationSeatsByIdConcertScheduleId(concertScheduleId);
    }

    @Override
    public List<Ticket> selectReservedSeats(Long concertScheduleId, List<Long> reservationIds) {
        return ticketJpaRepository.findAllByIdConcertScheduleIdAndIdReservationIdIn(concertScheduleId, reservationIds);
    }

    @Override
    @Transactional
    public void removeSeats(List<Long> reservationIds) {
        ticketJpaRepository.deleteByIdReservationIdIn(reservationIds);
    }
}
