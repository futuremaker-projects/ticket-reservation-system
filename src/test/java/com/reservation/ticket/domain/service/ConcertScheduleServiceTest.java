package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.entity.ConcertSchedule;
import com.reservation.ticket.domain.repository.ConcertScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
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
        LocalDateTime concertSchedule1 = LocalDateTime.of(2022, 5, 10, 2, 10);
        LocalDateTime concertSchedule2 = LocalDateTime.of(2022, 5, 20, 2, 10);
        List<ConcertSchedule> concertSchedules = List.of(
                ConcertSchedule.of(1L, 50, concertSchedule1),
                ConcertSchedule.of(2L, 50, concertSchedule2)
        );
        given(concertScheduleRepository.findAllByConcertId(concertId)).willReturn(concertSchedules);

        // when
        List<ConcertScheduleCommand.Get> schedules = sut.selectAllConcertSchedulesByConcertId(concertId);

        // then
        assertThat(schedules).isNotNull();
        assertThat(schedules.size()).isEqualTo(concertSchedules.size());
        assertThat(schedules)
                .extracting("id", "limitSeat", "openedAt")
                .containsExactlyInAnyOrder(
                        tuple(1L, 50, concertSchedule1),
                        tuple(2L, 50, concertSchedule2)
                );
    }

}
