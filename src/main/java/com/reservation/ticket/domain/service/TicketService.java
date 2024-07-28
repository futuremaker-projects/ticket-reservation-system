package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.entity.complex.TicketComplexIds;
import com.reservation.ticket.domain.repository.TicketRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public void save(Long reservationId, Long concertScheduleId, List<Long> seatIds, LockModeType lockModeType) {
        switch (lockModeType) {
            case NONE -> checkIfSeatsAvailable(concertScheduleId, seatIds);
            case PESSIMISTIC_READ -> checkIfSeatsAvailableWithPessimisticLock(concertScheduleId, seatIds);
        }

        seatIds.forEach(seatId -> {
            Ticket ticket = Ticket.of(
                    new TicketComplexIds(concertScheduleId, seatId, reservationId));
            ticketRepository.save(ticket);
        });
    }

    public void checkIfSeatsAvailable(Long concertScheduleId, List<Long> seatIds) {
        List<Ticket> tickets = ticketRepository.getSeats(concertScheduleId, seatIds);
        checkSeats(tickets);
    }
    public void checkIfSeatsAvailableWithPessimisticLock(Long concertScheduleId, List<Long> seatIds) {
        List<Ticket> tickets = ticketRepository.getSeatsWithPessimisticLock(concertScheduleId, seatIds);
        checkSeats(tickets);
    }

    public void checkSeats(List<Ticket> tickets) {
        if (!tickets.isEmpty()) {
            throw new ApplicationException(ErrorCode.SEAT_ALREADY_OCCUPIED, "seat already occupied : %s".formatted(tickets));
        }
    }

    public void releaseSeats(List<Long> reservationIds) {
        ticketRepository.removeSeats(reservationIds);
    }
}
