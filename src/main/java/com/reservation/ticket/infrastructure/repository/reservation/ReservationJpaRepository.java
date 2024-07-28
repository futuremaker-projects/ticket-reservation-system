package com.reservation.ticket.infrastructure.repository.reservation;

import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.enums.ReservationStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.reservationStatus = :reservationStatus order by r.id asc")
    List<Reservation> findAllByReservationStatusOrderByIdAsc(
            @Param("reservationStatus") ReservationStatus reservationStatus, PageRequest pageRequest
    );

    List<Reservation> findAllByReservationStatus(ReservationStatus reservationStatus);
}
