package com.reservation.ticket.interfaces.controller.dto.point;

public class PointDto {
    public record Response(int point) {
        public static Response of(int point) {
            return new Response(point);
        }
    }

    public record Request(Long userId, int point) {

    }
}
