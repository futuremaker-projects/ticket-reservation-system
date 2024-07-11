package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 *  콘서트 스케줄 통합 테스트
 */
@SpringBootTest
class ConcertScheduleUsecaseTest {

    @Autowired
    ConcertScheduleUsecase concertScheduleUsecase;

    @DisplayName("콘서트 id로 예약 날짜를 선택할 수 있는 콘서트 스케줄을 가져온다.")
    @Test
    void given_when_then() {
        // given
        // 토큰 검증을 위한 userId
        Long userId = 1L;
        Long concertId = 1L;

        // when
        List<ConcertScheduleCommand.Get> concertSchedules =
                concertScheduleUsecase.selectConcertSchedulesByConcertId(userId, concertId);

        // then
        assertThat(concertSchedules).isNotEmpty();
        assertThat(concertSchedules.size()).isEqualTo(3);

        assertThat(concertSchedules)
                .extracting("openedAt")
                .containsExactlyInAnyOrder(
                        tuple(LocalDateTime.of(2024, 7, 9, 9, 33, 40)),
                        tuple(LocalDateTime.of(2024, 7, 9, 12, 54, 40)),
                        tuple(LocalDateTime.of(2024, 7, 9, 18, 6, 50))
                );
    }

    @DisplayName("콘서트 스케줄 id로 50석의 모든 좌석의 목록을 조회한다.")
    @Test
    void givenUserIdAndConcertScheduleId_whenRequestingSeats_thenReturnConcertInfoAndSeats() {
        // given
        // 토큰 검증을 위한 userId
        Long userId = 1L;
        Long concertId = 1L;

        // when
        ConcertScheduleCommand.GetForSeats getForSeats = concertScheduleUsecase.selectSeatsByConcertScheduleId(userId, concertId);

        // then
        int availableSeats = 50;
        assertThat(getForSeats.seats()).isNotEmpty();
        assertThat(getForSeats.seats().size()).isEqualTo(availableSeats);
    }

}
