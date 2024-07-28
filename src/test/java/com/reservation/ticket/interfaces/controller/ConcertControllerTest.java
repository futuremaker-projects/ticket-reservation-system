package com.reservation.ticket.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.command.ConcertCommand;
import com.reservation.ticket.domain.entity.concert.ConcertService;
import com.reservation.ticket.domain.entity.queue.QueueService;
import com.reservation.ticket.interfaces.controller.dto.ConcertDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
@ExtendWith(SpringExtension.class)
class ConcertControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ConcertService concertService;
    @MockBean
    QueueService queueService;

    @DisplayName("콘서트 일정 목록을 조회한다.")
    @Test
    void givenNothing_whenRequestingConcertScheduleList_thenReturnsConcertScheduleList() throws Exception {
        // given
        List<ConcertCommand.Get> commandSeats = List.of(
                ConcertCommand.Get.of(1L, "concert1"),
                ConcertCommand.Get.of(2L, "concert2"),
                ConcertCommand.Get.of(3L, "concert3")
        );
        List<ConcertDto.Response> responses = commandSeats.stream().map(ConcertDto.Response::from).toList();
        given(concertService.selectAllConcerts()).willReturn(commandSeats);

        // when
        mockMvc.perform(get("/api/concerts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(responses)));

        // then
        then(concertService).should().selectAllConcerts();
    }

}

