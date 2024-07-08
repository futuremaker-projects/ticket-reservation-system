package com.reservation.ticket.infrastructure.repository.seat;

import com.reservation.ticket.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {
}
