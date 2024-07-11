package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.Seat;

import java.util.List;

public interface SeatRepository {

    List<Seat> findAllByConcertScheduleId(Long concertScheduleId);

    List<Seat> findByIdIn(List<Long> seatIds);

}
