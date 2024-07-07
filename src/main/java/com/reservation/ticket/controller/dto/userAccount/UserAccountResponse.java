package com.reservation.ticket.controller.dto.userAccount;

import java.time.LocalDateTime;

public record UserAccountResponse(Long id, String name, Integer point, LocalDateTime createdAt) {

    public static UserAccountResponse of(Long id, String name, Integer point) {
        return new UserAccountResponse(id, name, point, LocalDateTime.now());
    }

}
