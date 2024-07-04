package com.reservation.ticket.controller.dto.point;

public record PointResponseDto(Long id, Integer point) {

    public static PointResponseDto of(Long id, Integer point) {
        return new PointResponseDto(id, point);
    }

}
