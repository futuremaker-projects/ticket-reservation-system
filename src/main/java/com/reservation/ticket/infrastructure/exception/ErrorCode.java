package com.reservation.ticket.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CONTENT_NOT_FOUND"),
    CONFLICT(HttpStatus.CONFLICT, "CONFLICT"),

    USER_EXISTED(HttpStatus.CONFLICT, "USER_EXISTED"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED")

    ;

    public final HttpStatus httpStatus;
    public final String message;

}
