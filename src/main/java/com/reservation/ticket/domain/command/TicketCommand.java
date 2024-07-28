package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.concert.reservation.ticket.Ticket;
import com.reservation.ticket.domain.entity.concert.reservation.ticket.TicketComplexIds;

public class TicketCommand {

    public record Create(Long reservationId, Long concertScheduleId, Long seatId) {
        public Ticket to() {
            return Ticket.of(new TicketComplexIds(concertScheduleId, seatId, reservationId));
        }

        public Create of(Long reservationId, Long concertScheduleId, Long seatId) {
            return new Create(reservationId, concertScheduleId, seatId);
        }
    }

    public record Get(Long reservationId, Long concertScheduleId, Long seatId) {
        public static Get of(Long reservationId, Long concertScheduleId, Long seatId) {
            return new Get(reservationId, concertScheduleId, seatId);
        }

        public static Get from(Ticket ticket) {
            return Get.of(
                    ticket.getId().getConcertScheduleId(),
                    ticket.getId().getSeatId(),
                    ticket.getId().getReservationId()
            );
        }
    }

}
