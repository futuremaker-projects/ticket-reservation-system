package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;

import java.util.List;

public interface ReservationSeatRepository {

    ReservationSeat save(ReservationSeat reservationSeat);

    List<ReservationSeat> selectAllSeats();
    List<ReservationSeat> selectSeatsByScheduleIdWithPessimisticLock(Long concertScheduleId);
    List<ReservationSeat> selectSeatsByScheduleIdWithOptimisticLock(Long concertScheduleId);
    List<ReservationSeat> selectSeatsByScheduleId(Long concertScheduleId);

    List<ReservationSeat> selectReservedSeats(Long concertScheduleId, List<Long> reservationIds);

    void removeSeats(List<Long> reservationIds);
}
