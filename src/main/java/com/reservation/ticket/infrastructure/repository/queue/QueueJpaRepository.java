package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {

    Queue findByToken(String token);

    @Query("select count(q.id) from Queue q where q.queueStatus = :queueStatus")
    int countQueueByQueueStatus(@Param("queueStatus") QueueStatus queueStatus);

    @Query("select q from Queue q where q.queueStatus = :queueStatus")
    List<Queue> findAllByQueueStatus(@Param("queueStatus") QueueStatus queueStatus);

    @Query("select q from Queue q where q.queueStatus = :queueStatus order by q.id asc")
    List<Queue> findAllByQueueStatusOrderByIdAsc(@Param("queueStatus") QueueStatus queueStatus, Pageable pageable);
}
