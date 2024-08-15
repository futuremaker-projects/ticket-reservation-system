package com.reservation.ticket.infrastructure.repository.ticket;


import com.reservation.ticket.domain.entity.concert.reservation.ticket.Ticket;
import com.reservation.ticket.domain.entity.concert.reservation.ticket.TicketComplexIds;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<Ticket, TicketComplexIds> {

    List<Ticket> findAllByIdConcertScheduleIdAndIdSeatIdIn(Long concertScheduleId, List<Long> seats);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "1500"))
    @Query("select t from Ticket t where t.id.concertScheduleId = :concertScheduleId and t.id.seatId in :seats")
    List<Ticket> findAllWithPessimisticLock(@Param("concertScheduleId") Long concertScheduleId, @Param("seats") List<Long> seats);

    @Modifying
    @Query("DELETE FROM Ticket rs WHERE rs.id.reservationId IN :reservationIds")
    void deleteByIdReservationIdIn(@Param("reservationIds") List<Long> reservationIds);
}
