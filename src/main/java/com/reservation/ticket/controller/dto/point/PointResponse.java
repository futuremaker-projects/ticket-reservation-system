package com.reservation.ticket.controller.dto.point;

public record PointResponse(Integer point) {

    public static PointResponse of(Integer point) {
        return new PointResponse(point);
    }

}
