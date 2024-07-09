package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.UserAccount;

public class UserCommand {
    public record Get(Long id, String name, String token, int point) {
        public static Get of(Long id, String name, String token, int point) {
            return new Get(id, name, token, point);
        }
        public static Get of(Long id, String name) {
            return new Get(id, name, null, 0);
        }

        public static Get form(UserAccount userAccount) {
            return Get.of(userAccount.getId(), userAccount.getUsername());
        }
    }
}
