package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.repository.ReservationSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationSeatRepositoryImpl implements ReservationSeatRepository {

    private final ReservationSeatJpaRepository reservationSeatJpaRepository;

    @Override
    @Transactional
    public ReservationSeat save(ReservationSeat reservationSeat) {
        return reservationSeatJpaRepository.save(reservationSeat);
    }

    @Override
    public List<ReservationSeat> findAllByReservationIdIn(List<Long> reservationIds) {
        return reservationSeatJpaRepository.findAllByReservationIdIn(reservationIds);
    }
}
