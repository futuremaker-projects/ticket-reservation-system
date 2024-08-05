package com.reservation.ticket.infrastructure.repository.seat;

import com.reservation.ticket.domain.entity.concert.reservation.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByConcertScheduleId(Long concertScheduleId);

    List<Seat> findByIdIn(List<Long> seatIds);

}
