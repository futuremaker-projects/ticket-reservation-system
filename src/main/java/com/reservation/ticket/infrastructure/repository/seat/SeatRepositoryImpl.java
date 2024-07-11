package com.reservation.ticket.infrastructure.repository.seat;

import com.reservation.ticket.domain.entity.Seat;
import com.reservation.ticket.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> findAllByConcertScheduleId(Long concertScheduleId) {
        return seatJpaRepository.findAllByConcertScheduleId(concertScheduleId);
    }

    @Override
    public List<Seat> findByIdIn(List<Long> seatIds) {
        return seatJpaRepository.findByIdIn(seatIds);
    }

    @Override
    public List<Seat> findAllByReservationIdIn(List<Long> reservationIds) {
        return seatJpaRepository.findAllByReservationIdIn(reservationIds);
    }
}
