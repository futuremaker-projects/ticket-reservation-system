package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.concert.ConcertCommand;
import com.reservation.ticket.domain.command.concertSchedule.ConcertScheduleCommand;
import com.reservation.ticket.domain.entity.ConcertSchedule;
import com.reservation.ticket.domain.repository.ConcertScheduleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConcertScheduleServiceTest {

    @InjectMocks
    ConcertScheduleService sut;

    @Mock
    ConcertScheduleRepository concertScheduleRepository;

    @DisplayName("모든 콘서트 스케줄을 가져온다.")
    @Test
    void givenConcertId_whenRequestingConcertScheduleList_thenReturnConcertScheduleList() {
        // given

        // 콘서트 id가 1인 콘서트 스케줄을 전체 검색한다. 기간으로 한달로 변경하면 더욱 좋다.

        Long concertId = 1L;
        List<ConcertSchedule> concertSchedules = List.of(
                ConcertSchedule.of(1L, 50, LocalDateTime.of(2022, 5, 10, 2, 10)),
                ConcertSchedule.of(2L, 50, LocalDateTime.of(2022, 5, 20, 2, 10))
        );
        given(concertScheduleRepository.findAllByConcertId(concertId)).willReturn(concertSchedules);

        // when
        List<ConcertScheduleCommand.Select> schedules = sut.selectAllConcertSchedulesByConcertId(concertId);

        // then
        Assertions.assertThat(schedules).isNotNull();
    }

}