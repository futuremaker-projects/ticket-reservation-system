package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.entity.complex.ReservationSeatComplexIds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationSeatJpaRepository extends JpaRepository<ReservationSeat, ReservationSeatComplexIds> {

    List<ReservationSeat> findAllByIdReservationIdIn(List<Long> reservationIds);

}
