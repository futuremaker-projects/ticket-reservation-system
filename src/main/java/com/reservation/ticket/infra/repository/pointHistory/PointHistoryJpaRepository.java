package com.reservation.ticket.infra.repository.pointHistory;

import com.reservation.ticket.domain.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistory, Long> {
}
