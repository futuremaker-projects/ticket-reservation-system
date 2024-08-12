package com.reservation.ticket.domain.dto.command;

import com.reservation.ticket.domain.entity.userAccount.UserAccount;

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

    public record Use(int price, UserAccount userAccount) {
        public static Use of(int price, UserAccount userAccount) {
            return new Use(price, userAccount);
        }
    }
}
