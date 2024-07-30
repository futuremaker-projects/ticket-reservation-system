package com.reservation.ticket.application.usecase;

import com.reservation.ticket.application.dto.result.ConcertScheduleResult;
import com.reservation.ticket.application.dto.result.SeatResult;
import com.reservation.ticket.domain.dto.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.dto.command.SeatCommand;
import com.reservation.ticket.domain.dto.info.SeatInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  콘서트 스케줄 통합 테스트
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ConcertScheduleUsecaseTest {

    @Autowired
    ConcertScheduleUsecase concertScheduleUsecase;

    @DisplayName("콘서트 id로 예약 날짜를 선택할 수 있는 콘서트 스케줄을 가져온다.")
    @Test
    void given_when_then() {
        // given
        // 토큰 검증을 위한 userId
        String token = "734488355d85";
        Long concertId = 1L;

        // when
        List<ConcertScheduleResult> concertScheduleResults
                = concertScheduleUsecase.selectConcertSchedulesByConcertId(concertId, token);

        // then
        assertThat(concertScheduleResults).isNotEmpty();
        assertThat(concertScheduleResults.size()).isEqualTo(3);

        assertThat(concertScheduleResults)
                .extracting("openedAt")
                .containsExactlyInAnyOrder(
                        LocalDateTime.of(2024, 7, 9, 9, 33, 40),
                        LocalDateTime.of(2024, 7, 9, 12, 54, 40),
                        LocalDateTime.of(2024, 7, 9, 18, 6, 50)
                );
    }

    @DisplayName("콘서트 스케줄 id로 50석의 모든 좌석의 목록을 조회한다.")
    @Test
    void givenUserIdAndConcertScheduleId_whenRequestingSeats_thenReturnConcertInfoAndSeats() {
        // given
        // 토큰 검증을 위한 userId
        String token = "734488355d85";
        Long concertId = 1L;

        // when
        List<SeatResult> seats = concertScheduleUsecase.selectSeatsByConcertScheduleId(concertId, token);

        // then
        int availableSeats = 50;
        assertThat(seats).isNotEmpty();
        assertThat(seats.size()).isEqualTo(availableSeats);
    }

}
