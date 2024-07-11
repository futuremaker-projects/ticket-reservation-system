package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.complex.ScheduleSeat;
import com.reservation.ticket.domain.entity.complex.ScheduleSeatComplexIds;

public class ScheduleSeatCommand {

    public record Create(Long reservationId, Long concertScheduleId, Long seatId) {
        public ScheduleSeat to() {
            return ScheduleSeat.of(new ScheduleSeatComplexIds(concertScheduleId, seatId), reservationId);
        }

        public Create of(Long reservationId, Long concertScheduleId, Long seatId) {
            return new Create(reservationId, concertScheduleId, seatId);
        }
    }

    public record Get(Long reservationId, Long concertScheduleId, Long seatId) {
        public static Get of(Long reservationId, Long concertScheduleId, Long seatId) {
            return new Get(reservationId, concertScheduleId, seatId);
        }

        public static Get from(ScheduleSeat scheduleSeat) {
            return Get.of(
                    scheduleSeat.getId().getConcertScheduleId(),
                    scheduleSeat.getId().getSeatId(),
                    scheduleSeat.getReservationId()
            );
        }
    }

}
