package com.reservation.ticket.infrastructure.repository.concertSchedule;

import com.reservation.ticket.domain.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule, Long> {
}
