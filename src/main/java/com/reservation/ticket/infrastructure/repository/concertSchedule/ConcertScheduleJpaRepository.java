package com.reservation.ticket.infrastructure.repository.concertSchedule;

import com.reservation.ticket.domain.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule, Long> {

    List<ConcertSchedule> findAllByConcertId(Long concertId);

    Optional<ConcertSchedule> findByOpenedAt(LocalDateTime openedAt);
}
