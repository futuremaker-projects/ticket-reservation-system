package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.entity.complex.ReservationSeatComplexIds;

public class ReservationSeatCommand {

    public record Create(Long reservationId, Long concertScheduleId, Long seatId) {
        public ReservationSeat to() {
            return ReservationSeat.of(new ReservationSeatComplexIds(concertScheduleId, seatId, reservationId));
        }

        public Create of(Long reservationId, Long concertScheduleId, Long seatId) {
            return new Create(reservationId, concertScheduleId, seatId);
        }
    }

    public record Get(Long reservationId, Long concertScheduleId, Long seatId) {
        public static Get of(Long reservationId, Long concertScheduleId, Long seatId) {
            return new Get(reservationId, concertScheduleId, seatId);
        }

        public static Get from(ReservationSeat reservationSeat) {
            return Get.of(
                    reservationSeat.getId().getConcertScheduleId(),
                    reservationSeat.getId().getSeatId(),
                    reservationSeat.getId().getReservationId()
            );
        }
    }

}
