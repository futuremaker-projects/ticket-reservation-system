package com.reservation.ticket.infrastructure.repository.ticket;

import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.entity.complex.TicketComplexIds;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<Ticket, TicketComplexIds> {

    List<Ticket> findAllByIdConcertScheduleIdAndIdReservationIdIn(Long concertScheduleId, List<Long> reservationIds);

    @Query("select rs from Ticket rs where rs.id.concertScheduleId = :concertScheduleId")
    List<Ticket> findReservationSeatsByIdConcertScheduleId(@Param("concertScheduleId") Long concertScheduleId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "1500"))
    @Query("select rs from Ticket rs where rs.id.concertScheduleId = :concertScheduleId")
    List<Ticket> findAllByIdConcertScheduleIdWithPessimisticLock(Long concertScheduleId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select rs from Ticket rs where rs.id.concertScheduleId = :concertScheduleId")
    List<Ticket> findAllByIdConcertScheduleIdWithOptimisticLock(@Param("concertScheduleId") Long concertScheduleId);

    @Modifying
    @Query("DELETE FROM Ticket rs WHERE rs.id.reservationId IN :reservationIds")
    void deleteByIdReservationIdIn(@Param("reservationIds") List<Long> reservationIds);

}
