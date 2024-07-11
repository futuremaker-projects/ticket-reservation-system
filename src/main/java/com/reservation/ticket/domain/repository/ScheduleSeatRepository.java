package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.complex.ScheduleSeat;

import java.util.List;

public interface ScheduleSeatRepository {

    ScheduleSeat save(ScheduleSeat scheduleSeat);

    List<ScheduleSeat> findAllByReservationIdIn(List<Long> reservationIds);

}
