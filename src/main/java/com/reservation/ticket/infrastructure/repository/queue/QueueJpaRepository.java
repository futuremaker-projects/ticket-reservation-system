package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {

    @Query("select count(q.id) from Queue q where q.status = :status")
    int countQueueByQueueStatus(@Param("status") QueueStatus status);

    Queue findByToken(String token);
}
