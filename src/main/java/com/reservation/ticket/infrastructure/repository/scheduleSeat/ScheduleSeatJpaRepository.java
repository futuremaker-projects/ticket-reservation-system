package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.ScheduleSeat;
import com.reservation.ticket.domain.entity.complex.ScheduleSeatComplexIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleSeatJpaRepository extends JpaRepository<ScheduleSeat, ScheduleSeatComplexIds> {
}
