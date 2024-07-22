package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.entity.complex.ReservationSeatComplexIds;
import com.reservation.ticket.domain.repository.ReservationSeatRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSeatService {

    private final ReservationSeatRepository reservationSeatRepository;

    public void save(Long reservationId, Long concertScheduleId, List<Long> seatIds) {
        List<ReservationSeat> reservationSeats = reservationSeatRepository.selectReservedSeatsByConcertScheduleId(concertScheduleId);
        List<Long> reservedSeatIds = reservationSeats.stream().map(reservationSeat -> reservationSeat.getId().getSeatId()).toList();
        ArrayList<Long> copiedSeatIds = new ArrayList<>(seatIds);
        copiedSeatIds.retainAll(reservedSeatIds);       // 이미 예약된 좌석이면 예외처리 한다.
        if (!copiedSeatIds.isEmpty()) {
            throw new ApplicationException(ErrorCode.SEAT_ALREADY_OCCUPIED, "seat already occupied : %s".formatted(copiedSeatIds));
        }

        seatIds.forEach(seatId -> {
            ReservationSeat reservationSeat = ReservationSeat.of(
                    new ReservationSeatComplexIds(concertScheduleId, seatId, reservationId));

            reservationSeatRepository.save(reservationSeat);
        });
    }

}
