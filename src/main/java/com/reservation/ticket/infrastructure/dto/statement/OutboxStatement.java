package com.reservation.ticket.infrastructure.dto.statement;

public class OutboxStatement {

    public record Save(String id, String message) {
        public static Save of(String id, String message) {
            return new Save(id, message);
        }
    }

    public record Get(String id) {
        public static Get of(String id) {
            return new Get(id);
        }
    }
}
