package com.reservation.ticket.utils;

import java.util.UUID;

public class DummyData {

    public static String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }

}
