package com.reservation.ticket.domain.entity.concert.reservation;

import java.util.List;

public interface SeatRepository {

    List<Seat> findAllByConcertScheduleId(Long concertScheduleId);

    List<Seat> findByIdIn(List<Long> seatIds);


}
