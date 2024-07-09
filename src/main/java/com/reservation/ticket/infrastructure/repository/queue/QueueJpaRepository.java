package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {
}
