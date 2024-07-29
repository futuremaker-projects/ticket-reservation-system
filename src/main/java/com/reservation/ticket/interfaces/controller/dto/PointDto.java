package com.reservation.ticket.interfaces.controller.dto;

import com.reservation.ticket.domain.dto.command.PointCommand;

public class PointDto {
    public record Response(int point) {
        public static Response of(int point) {
            return new Response(point);
        }

        public static Response from(PointCommand.Get point) {
            return Response.of(point.point());
        }
    }

    public record Request(int point) {
        public static Request of(int point) {
            return new Request(point);
        }
    }
}
