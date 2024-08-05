package com.reservation.ticket.infrastructure.repository.seat;

import com.reservation.ticket.domain.entity.concert.reservation.Seat;
import com.reservation.ticket.domain.entity.concert.reservation.SeatRepository;
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
}
