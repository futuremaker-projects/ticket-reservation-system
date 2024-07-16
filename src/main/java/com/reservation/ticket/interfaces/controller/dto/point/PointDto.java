package com.reservation.ticket.interfaces.controller.dto.point;

import com.reservation.ticket.domain.command.PointCommand;

public class PointDto {
    public record Response(int point) {
        public static Response of(int point) {
            return new Response(point);
        }
    }

    public record Request(Long userId, int point) {
        public PointCommand.Update toPointCommandUpdate() {
            return PointCommand.Update.of(userId, point);
        };
    }
}
