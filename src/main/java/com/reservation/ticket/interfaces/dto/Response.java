package com.reservation.ticket.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private String resultCode;
    private T data;

    public static <T> Response<T> success() {
        return new Response<T>("SUCCESS", null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>("SUCCESS", data);
    }

    public static <T> Response<T> error(T data) {
        return new Response<T>("ERROR", data);
    }

}
