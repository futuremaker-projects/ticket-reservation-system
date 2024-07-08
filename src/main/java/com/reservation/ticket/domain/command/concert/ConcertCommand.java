package com.reservation.ticket.domain.command.concert;

import com.reservation.ticket.domain.entity.Concert;

public class ConcertCommand {
    public record Select(Long id, String name) {

        public static Select of(Long id, String name) {
            return new Select(id, name);
        }
        public static Select from(Concert concert) {
            return Select.of(concert.getId(), concert.getName());
        }
    }

    public record Update() {

    }

    public record Create() {

    }
}
