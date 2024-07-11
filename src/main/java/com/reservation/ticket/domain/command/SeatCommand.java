package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.Seat;

import java.time.LocalDateTime;

public class SeatCommand {

    public record Get(Long id, int seatNumber, LocalDateTime occupiedAt, boolean occupied) {
        public static Get of(Long id, int seatNumber, LocalDateTime occupiedAt, boolean occupied) {
            return new Get(id, seatNumber, occupiedAt, occupied);
        }

        public static Get from(Seat seat) {
            return Get.of(seat.getId(), seat.getSeatNumber(), seat.getOccupiedAt(), seat.isOccupied());
        }
    }
}
