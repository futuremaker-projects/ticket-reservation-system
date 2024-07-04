package com.reservation.ticket.controller.dto.point;

public record PointResponseDto(Integer point) {

    public static PointResponseDto of(Integer point) {
        return new PointResponseDto(point);
    }

}
