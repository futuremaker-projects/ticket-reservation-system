package com.reservation.ticket.domain.command;

public class PointCommand {
    public record Get(int point) {
        public static Get of(int point) {
            return new Get(point);
        }
    }
}
