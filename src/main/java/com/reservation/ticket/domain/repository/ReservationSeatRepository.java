package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;

import java.util.List;

public interface ReservationSeatRepository {

    ReservationSeat save(ReservationSeat reservationSeat);

    List<ReservationSeat> findAllByReservationIdIn(List<Long> reservationIds);

}
