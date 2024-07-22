package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.entity.complex.ReservationSeatComplexIds;
import com.reservation.ticket.domain.repository.ReservationSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSeatService {

    private final ReservationSeatRepository reservationSeatRepository;

    public void save(Long reservationId, Long concertScheduleId, List<Long> seatIds) {
        seatIds.forEach(seatId -> {
            ReservationSeat reservationSeat = ReservationSeat.of(
                    new ReservationSeatComplexIds(concertScheduleId, seatId), reservationId);
            reservationSeatRepository.save(reservationSeat);
        });
    }

}
