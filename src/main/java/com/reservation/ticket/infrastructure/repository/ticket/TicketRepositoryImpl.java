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
    public List<Ticket> getSeats(Long concertScheduleId, List<Long> seats) {
        return ticketJpaRepository.findAllByIdConcertScheduleIdAndIdSeatIdIn(concertScheduleId, seats);
    }

    @Override
    public List<Ticket> getSeatsWithPessimisticLock(Long concertScheduleId, List<Long> seats) {
        return ticketJpaRepository.findAllWithPessimisticLock(concertScheduleId, seats);
    }

    @Override
    @Transactional
    public void removeSeats(List<Long> reservationIds) {
        ticketJpaRepository.deleteByIdReservationIdIn(reservationIds);
    }
}
