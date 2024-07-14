package com.reservation.ticket.infrastructure.repository.reservation;

import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.repository.ReservationRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

    @Override
    public List<Reservation> findAllByReservationStatusOrderByIdAsc(ReservationStatus reservationStatus, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return reservationJpaRepository.findAllByReservationStatusOrderByIdAsc(reservationStatus,pageRequest);
    }

    @Override
    public List<Reservation> findAllByReservationStatus(ReservationStatus reservationStatus) {
        return reservationJpaRepository.findAllByReservationStatus(reservationStatus);
    }

    @Override
    public Reservation findById(Long reservationId) {
        return reservationJpaRepository.findById(reservationId).orElseThrow(
                () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND, "Reservation not found, id : %d".formatted(reservationId)));
    }
}
