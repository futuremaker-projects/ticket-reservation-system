package com.reservation.ticket.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueueStatus {

    ACTIVE("활성화"),
    WAIT("대기"),
    EXPIRED("만료"),

    ;

    public final String korValue;

}
