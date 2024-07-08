package com.reservation.ticket.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ConcertController.class)
@ExtendWith(SpringExtension.class)
class ConcertControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @DisplayName("콘서트 일정 목록을 조회한다.")
    @Test
    void givenNothing_whenRequestingConcertScheduleList_thenReturnsConcertScheduleList() throws Exception {
//        // given
//        LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 20, 2, 10);
//        List<Concert.Select> concerts = List.of(
//                ConcertCommand.Select.of(1L, 10, localDateTime, (1L, "concert1")),
//            ConcertScheduleResponse.of(2L, 10,
//            , ConcertResponse.of(2L, "concert2"))
//        );
//
//        // when
//        mockMvc.perform(get("/concerts")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().bytes(objectMapper.writeValueAsBytes(concerts)));
//
//        // then

    }

    @DisplayName("콘서트 일정 id로 콘서트 일정를 조회한다.")
    @Test
    public void givenConcertScheduleId_whenRequestingConcertSchedule_thenReturnsConcertSchedule() throws Exception {
//        // given
//        ConcertScheduleResponse concert =
//                ConcertScheduleResponse.of(1L, 10, LocalDateTime.of(2022, 5, 20, 2, 10), ConcertResponse.of(1L, "concert1"));
//        Long concertId = 1L;
//        // when
//
//        mockMvc.perform(get("/concerts/%d".formatted(concertId))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().bytes(objectMapper.writeValueAsBytes(concert)));
//
//        // then
    }

}