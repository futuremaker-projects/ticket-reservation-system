package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.Concert;

public class ConcertCommand {
    public record Get(Long id, String name) {

        public static Get of(Long id, String name) {
            return new Get(id, name);
        }
        public static Get from(Concert concert) {
            return Get.of(concert.getId(), concert.getName());
        }
    }

    public record Update() {

    }

    public record Create() {

    }
}
