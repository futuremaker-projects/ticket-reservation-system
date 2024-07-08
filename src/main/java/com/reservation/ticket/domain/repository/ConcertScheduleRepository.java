package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.ConcertSchedule;

import java.util.List;

public interface ConcertScheduleRepository {

    List<ConcertSchedule> findAllConcertSchedules();
    List<ConcertSchedule> findAllByConcertId(Long concertId);

}
