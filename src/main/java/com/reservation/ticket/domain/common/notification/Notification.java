package com.reservation.ticket.domain.common.notification;

public class Notification {

    public record Send(String message) {
        public static Send of(String message) {
            return new Send(message);
        }
    }

}
