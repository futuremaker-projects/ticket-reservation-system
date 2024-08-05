package com.reservation.ticket.domain.dto.command;

public class PointCommand {
    public record Get(int point) {
        public static Get of(int point) {
            return new Get(point);
        }
    }

    public record Update(Long userId, int point) {
        public static Update of(Long userId, Integer point) {
            return new Update(userId, point);
        }
    }
}
