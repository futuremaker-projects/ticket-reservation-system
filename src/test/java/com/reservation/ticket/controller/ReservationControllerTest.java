package com.reservation.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.controller.dto.concert.ConcertResponse;
import com.reservation.ticket.controller.dto.concertSchedule.ConcertScheduleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ReservationController.class)
@ExtendWith(SpringExtension.class)
class ReservationControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @DisplayName("")
    @Test
    void given_when_then() throws Exception {
        // given
        List<LocalDateTime> concertDateList = List.of(
                LocalDateTime.of(2022, 5, 10, 2, 10),
                LocalDateTime.of(2022, 5, 20, 2, 10),
                LocalDateTime.of(2022, 5, 25, 2, 10)
        );

        // when
        mockMvc.perform(get("/reservation/concert-schedule/{concertScheduleId}/available-date")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(selectConcertScheduleResponseDtoList())));

        // then
    }

    public static List<ConcertScheduleResponse> selectConcertScheduleResponseDtoList() {
        return List.of(
                ConcertScheduleResponse.of(
                        1L, 50, LocalDateTime.of(2022, 5, 10, 2, 10),
                        ConcertResponse.of(1L, "concert1")
                ),
                ConcertScheduleResponse.of(
                        2L, 50, LocalDateTime.of(2022, 5, 20, 2, 10),
                        ConcertResponse.of(1L, "concert1")
                )
        );
    }

}