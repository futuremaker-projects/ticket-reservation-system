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
    public ReservationSeat save(ReservationSeat reservationSeat) {
        return reservationSeatJpaRepository.save(reservationSeat);
    }

    @Override
    public List<ReservationSeat> selectAllSeats() {
        return reservationSeatJpaRepository.findAll();
    }

    @Override
    public List<ReservationSeat> selectSeatsByScheduleIdWithPessimisticLock(Long concertScheduleId) {
        return reservationSeatJpaRepository.findAllByIdConcertScheduleId(concertScheduleId);
    }

    @Override
    public List<ReservationSeat> selectSeatsByScheduleId(Long concertScheduleId) {
        return reservationSeatJpaRepository.findReservationSeatsByIdConcertScheduleId(concertScheduleId);
    }

    @Override
    public List<ReservationSeat> selectReservedSeats(Long concertScheduleId, List<Long> reservationIds) {
        return reservationSeatJpaRepository.findAllByIdConcertScheduleIdAndIdReservationIdIn(concertScheduleId, reservationIds);
    }

    @Override
    @Transactional
    public void removeSeats(List<Long> reservationIds) {
        reservationSeatJpaRepository.deleteByIdReservationIdIn(reservationIds);
    }
}
