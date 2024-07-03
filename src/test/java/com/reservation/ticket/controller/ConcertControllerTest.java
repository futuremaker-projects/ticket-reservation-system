package com.reservation.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.controller.dto.concert.ConcertResponseDto;
import com.reservation.ticket.controller.dto.concertInfo.ConcertInfoResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
@ExtendWith(SpringExtension.class)
class ConcertControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void givenNothing_whenRequestingConcertList_thenReturnsConcertList() throws Exception {
        // given

        List<ConcertResponseDto> concerts = List.of(
            new ConcertResponseDto(1L, 10, LocalDateTime.now(), ConcertInfoResponseDto.of(1L, "concert1")),
            new ConcertResponseDto(2L, 10, LocalDateTime.now(), ConcertInfoResponseDto.of(2L, "concert2"))
        );

        // when
        mockMvc.perform(get("/concerts")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(concerts)));

        // then
    }

}