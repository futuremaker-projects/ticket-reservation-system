package com.reservation.ticket.infrastructure.repository.concertSchedule;

import com.reservation.ticket.domain.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule, Long> {

    List<ConcertSchedule> findAllByConcertId(Long concertId);

}
