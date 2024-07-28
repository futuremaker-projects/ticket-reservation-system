package com.reservation.ticket.infrastructure.repository.pointHistory;

import com.reservation.ticket.domain.entity.point.pointhistory.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistory, Long> {
}
