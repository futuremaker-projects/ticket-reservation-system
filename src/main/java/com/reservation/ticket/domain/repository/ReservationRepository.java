package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);
}
