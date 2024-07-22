package com.reservation.ticket.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.application.usecase.ConcertScheduleUsecase;
import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.interfaces.controller.dto.ConcertScheduleDto;
import com.reservation.ticket.interfaces.controller.dto.SeatDto;
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
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertScheduleController.class)
@ExtendWith(SpringExtension.class)
class ConcertScheduleControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ConcertScheduleUsecase concertScheduleUsecase;
    @MockBean
    QueueService queueService;

    @DisplayName("콘서트 id를 이용하여 콘서트 스케줄 id를 조회한다.")
    @Test
    void givenConcertId_whenRequestingConcertSchedules_thenReturnConcertSchedule() throws Exception {
        // given
        String token = "27c4c82ba7c3";
        Long queueId = 1L;
        Queue queue = Queue.of(queueId, token, QueueStatus.ACTIVE);
        given(queueService.getQueueByToken(token)).willReturn(queue);

        Long concertId = 1L;
        List<ConcertScheduleCommand.Get> concertScheduleCommands = List.of(
                ConcertScheduleCommand.Get.of(1L, 50, LocalDateTime.now()),
                ConcertScheduleCommand.Get.of(1L, 50, LocalDateTime.now())
        );
        List<ConcertScheduleDto.Response> responses = concertScheduleCommands.stream().map(ConcertScheduleDto.Response::from).toList();
        given(concertScheduleUsecase.selectConcertSchedulesByConcertId(concertId, token)).willReturn(concertScheduleCommands);

        // when
        mockMvc.perform(get("/api/concertSchedules/concerts/%d".formatted(concertId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(responses)));

        // then
        then(queueService).should().getQueueByToken(token);
        then(concertScheduleUsecase).should().selectConcertSchedulesByConcertId(concertId, token);
    }

    @DisplayName("콘서트 스케줄러 id로 콘서트 스케즐 정보와 좌석 목록조회")
    @Test
    void given_when_then() throws Exception {
        // given
        String token = "734488355d85";
        Long queueId = 1L;
        Queue queue = Queue.of(queueId, token, QueueStatus.ACTIVE);
        given(queueService.getQueueByToken(token)).willReturn(queue);

        Long concertScheduleId = 1L;
        List<SeatCommand.Get> commandSeats = List.of(
                SeatCommand.Get.of(1L, 1L, null, false),
                SeatCommand.Get.of(2L, 1L, null, false),
                SeatCommand.Get.of(3L, 1L, null, false)
        );
        List<SeatDto.Response> responses = commandSeats.stream().map(SeatDto.Response::from).toList();
        given(concertScheduleUsecase.selectSeatsByConcertScheduleId(concertScheduleId, token)).willReturn(commandSeats);

        // when
        mockMvc.perform(get("/api/concertSchedules/%d/seats".formatted(concertScheduleId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(responses)));

        // then
        then(concertScheduleUsecase).should().selectSeatsByConcertScheduleId(concertScheduleId, token);

    }


}