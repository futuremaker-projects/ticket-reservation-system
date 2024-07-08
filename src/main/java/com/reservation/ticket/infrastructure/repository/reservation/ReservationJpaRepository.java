package com.reservation.ticket.infrastructure.repository.reservation;

import com.reservation.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
}
