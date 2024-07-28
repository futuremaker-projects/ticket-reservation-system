package com.reservation.ticket.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.entity.queue.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.queue.QueueService;
import com.reservation.ticket.interfaces.controller.dto.QueueDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QueueController.class)
@ExtendWith(SpringExtension.class)
class QueueControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    QueueService queueService;

    @DisplayName("사용자의 정보를 이용하여 최초 대기열 데이터 생성한다.")
    @Test
    void given_when_then() throws Exception {
        // given
        String token = "734488355d85";
        Long queueId = 1L;
        Queue queue = Queue.of(queueId, token, QueueStatus.ACTIVE);
        given(queueService.getQueueByToken(token)).willReturn(queue);

        Long userId = 1L;
        QueueDto.Request request = QueueDto.Request.of(userId);

        QueueCommand.Get queueCommand = QueueCommand.Get.of(1L, token, QueueStatus.WAIT,
                LocalDateTime.of(2022, 5, 20, 2, 10),
                LocalDateTime.of(2022, 5, 20, 2, 10));
        given(queueService.createQueue(userId)).willReturn(queueCommand);

        QueueDto.Response response = QueueDto.Response.from(queueCommand);

        // when
        mockMvc.perform(post("/api/queue/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(response)));

        // then
        then(queueService).should().createQueue(userId);
    }

}