package com.reservation.ticket.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.application.dto.result.ReservationResult;
import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.entity.queue.QueueService;
import com.reservation.ticket.interfaces.dto.ReservationDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ReservationController.class)
@ExtendWith(SpringExtension.class)
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReservationUsecase reservationUsecase;
    @MockBean
    QueueService queueService;

    @DisplayName("콘서트 스케줄 id와 자리 id를 받아 예약 생성한다.")
    @Test
    void given_when_then() throws Exception {
        // given
        String token = "734488355d85";
        Long queueId = 1L;
        QueueCommand.Get get = QueueCommand.Get.of(queueId, token, QueueStatus.ACTIVE);
        given(queueService.getQueueByToken(token)).willReturn(get);

        Long concertScheduleId = 1L;
        List<Long> seatIds = List.of(1L, 2L, 3L);
        int price = 1000;
        ReservationDto.Request request = ReservationDto.Request.of(concertScheduleId, seatIds, price);

        Long reservationId = 1L;
        ReservationResult reservation = ReservationResult.of(reservationId, price, PaymentStatus.NOT_PAID, ReservationStatus.ACTIVE, LocalDateTime.now());

        given(reservationUsecase.makeReservation(request.toCriteria(), token)).willReturn(reservation);

        // when
        mockMvc.perform(post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print());

        // then
        then(reservationUsecase).should().makeReservation(request.toCriteria(), token);
    }

}