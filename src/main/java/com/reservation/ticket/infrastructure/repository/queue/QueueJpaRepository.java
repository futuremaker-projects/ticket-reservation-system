package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.domain.enums.QueueStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {

    QueueEntity findByToken(String token);

    @Query("select count(q.id) from QueueEntity q where q.queueStatus = :queueStatus")
    int countQueueByQueueStatus(@Param("queueStatus") QueueStatus queueStatus);

    @Query("select q from QueueEntity q where q.queueStatus = :queueStatus")
    List<QueueEntity> findAllByQueueStatus(@Param("queueStatus") QueueStatus queueStatus);

    @Query("select q from QueueEntity q where q.queueStatus = :queueStatus order by q.id asc")
    List<QueueEntity> findAllByQueueStatusOrderByIdAsc(@Param("queueStatus") QueueStatus queueStatus, Pageable pageable);

    @EntityGraph(attributePaths = "userAccount")
    Optional<QueueEntity> findByUserAccount_Id(Long userId);
}
