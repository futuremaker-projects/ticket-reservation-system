package com.reservation.ticket.domain.entity.concert.reservation;

import com.reservation.ticket.domain.enums.ReservationStatus;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAllByReservationStatusOrderByIdAsc(ReservationStatus reservationStatus, int limit);

    List<Reservation> findAllByReservationStatus(ReservationStatus reservationStatus);

    Reservation findById(Long reservationId);
}
