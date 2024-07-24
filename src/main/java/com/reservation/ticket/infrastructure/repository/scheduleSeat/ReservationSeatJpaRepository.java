package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.entity.complex.ReservationSeatComplexIds;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationSeatJpaRepository extends JpaRepository<ReservationSeat, ReservationSeatComplexIds> {

    List<ReservationSeat> findAllByIdConcertScheduleIdAndIdReservationIdIn(Long concertScheduleId, List<Long> reservationIds);

    @Query("select rs from ReservationSeat rs where rs.id.concertScheduleId = :concertScheduleId")
    List<ReservationSeat> findReservationSeatsByIdConcertScheduleId(@Param("concertScheduleId") Long concertScheduleId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "1500"))
    @Query("select rs from ReservationSeat rs where rs.id.concertScheduleId = :concertScheduleId")
    List<ReservationSeat> findAllByIdConcertScheduleIdWithPessimisticLock(Long concertScheduleId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select rs from ReservationSeat rs where rs.id.concertScheduleId = :concertScheduleId")
    List<ReservationSeat> findAllByIdConcertScheduleIdWithOptimisticLock(@Param("concertScheduleId") Long concertScheduleId);

    @Modifying
    @Query("DELETE FROM ReservationSeat rs WHERE rs.id.reservationId IN :reservationIds")
    void deleteByIdReservationIdIn(@Param("reservationIds") List<Long> reservationIds);

}
