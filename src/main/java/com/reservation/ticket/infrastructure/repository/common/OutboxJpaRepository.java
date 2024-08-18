package com.reservation.ticket.infrastructure.repository.common;

import com.reservation.ticket.domain.enums.OutboxType;
import com.reservation.ticket.infrastructure.dto.entity.OutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<OutboxEntity, String> {

    List<OutboxEntity> findAllByOutboxType(OutboxType outboxType);

    @Modifying
    @Query("UPDATE OutboxEntity o SET o.outboxType = :outboxType, o.updatedAt = now() WHERE o.id = :outboxId")
    void update(@Param("outboxId") String outboxId, @Param("outboxType") OutboxType outboxType);

}
