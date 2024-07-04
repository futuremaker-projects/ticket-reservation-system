package com.reservation.ticket.controller.dto.user;

import java.time.LocalDateTime;

public record User(Long id, String name, Integer point, LocalDateTime createdAt) {

    public static User of(Long id, String name, Integer point) {
        return new User(id, name, point, LocalDateTime.now());
    }

}
