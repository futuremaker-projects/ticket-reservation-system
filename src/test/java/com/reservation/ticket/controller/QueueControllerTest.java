package com.reservation.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.controller.dto.queue.TokenStatus;
import com.reservation.ticket.controller.dto.queue.QueueResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QueueController.class)
@ExtendWith(SpringExtension.class)
class QueueControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void given_when_then() throws Exception {
        // given
        QueueResponse queue = QueueResponse.of(1L, 1L, "UUID-1", TokenStatus.WAIT,
                LocalDateTime.of(2022, 5, 20, 2, 10),
                LocalDateTime.of(2022, 5, 20, 2, 10));

        // when
        mockMvc.perform(post("/queue/token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(queue)));

        // then
    }

}