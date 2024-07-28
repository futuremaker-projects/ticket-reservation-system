package com.reservation.ticket.domain.entity.concert.concertSchedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertScheduleRepository {

    List<ConcertSchedule> findAllConcertSchedules();
    List<ConcertSchedule> findAllByConcertId(Long concertId);

    ConcertSchedule findByOpenedAt(LocalDateTime openedAt);
    ConcertSchedule findById(Long id);

}
