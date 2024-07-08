package com.reservation.ticket.infrastructure.repository.concert;

import com.reservation.ticket.domain.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
}
