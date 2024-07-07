package com.reservation.ticket.infra.repository.concert;

import com.reservation.ticket.domain.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
}
